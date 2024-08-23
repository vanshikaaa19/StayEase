package com.crio.stayease.security.config;

import com.crio.stayease.security.auditing.ApplicationAuditAware;
import com.crio.stayease.security.entity.Users;
import com.crio.stayease.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up Spring Security components.
 *
 * <p>This class configures the necessary beans for user authentication, password encoding,
 * auditing, and manages the user details service used by Spring Security.</p>
 */
@Configuration
@RequiredArgsConstructor
@Log4j2
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * Provides a {@link UserDetailsService} bean to load user-specific data.
     *
     * <p>This method returns an implementation of {@link UserDetailsService} that loads user details
     * from the database using the {@link UserRepository}. It throws a {@link UsernameNotFoundException}
     * if the user is not found. It also logs the loaded user details for debugging purposes.</p>
     *
     * @return a {@link UserDetailsService} instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Users user = repository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            log.info("Loaded user " + email + " with roles: " + user.getRole());
            return user;
        };
    }

    /**
     * Provides a {@link DaoAuthenticationProvider} bean for authentication.
     *
     * <p>This method sets up a {@link DaoAuthenticationProvider} with the configured user details
     * service and password encoder. It is used by Spring Security to authenticate users based on
     * credentials stored in the database.</p>
     *
     * @return a {@link DaoAuthenticationProvider} instance
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides an {@link AuditorAware} bean for auditing.
     *
     * <p>This method returns an {@link ApplicationAuditAware} instance, which is used for
     * auditing purposes to keep track of the current user performing actions.</p>
     *
     * @return an {@link AuditorAware} instance
     */
    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }

    /**
     * Provides an {@link AuthenticationManager} bean.
     *
     * <p>This method creates and returns an {@link AuthenticationManager} instance using the
     * {@link AuthenticationConfiguration} provided by Spring Security.</p>
     *
     * @param config the {@link AuthenticationConfiguration} instance
     * @return an {@link AuthenticationManager} instance
     * @throws Exception if there is an error creating the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Provides a {@link PasswordEncoder} bean.
     *
     * <p>This method returns a {@link BCryptPasswordEncoder} instance used for encoding and
     * verifying passwords in a secure manner.</p>
     *
     * @return a {@link PasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
