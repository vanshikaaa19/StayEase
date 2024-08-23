package com.crio.stayease.security.config;

import com.crio.stayease.security.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Configuration class for setting up Spring Security for the application.
 * <p>
 * This configuration class defines security filters, authorization rules, and exception handling for
 * the application. It sets up JWT authentication, configures role-based access controls, and manages
 * session policies.
 * </p>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/auth/**"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final DaoAuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    /**
     * Configures the HTTP security for the application.
     * <p>
     * This method sets up various security aspects including:
     * <ul>
     *     <li>Disabling CSRF protection.</li>
     *     <li>Configuring URL patterns and authorization rules.</li>
     *     <li>Setting up custom JWT authentication filter.</li>
     *     <li>Configuring logout behavior and handlers.</li>
     *     <li>Enabling stateless session management.</li>
     *     <li>Adding a custom request logging filter before the authentication filter.</li>
     * </ul>
     * </p>
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                // Hotel Controller
                                .requestMatchers(HttpMethod.GET, "/hotels/**").hasAnyAuthority(Role.CUSTOMER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/hotels").hasAuthority(Role.CUSTOMER.name())
                                // Authentication Controller
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/refresh-token", "/api/v1/auth/login").permitAll()
                                // Booking Controller
                                .requestMatchers(HttpMethod.PUT, "/bookings/{id}/cancel").hasAnyAuthority(Role.MANAGER.name(), Role.ADMIN.name())
                                // User Controller
                                .requestMatchers(HttpMethod.GET, "/users/me").hasAnyAuthority(Role.CUSTOMER.name(), Role.MANAGER.name(), Role.ADMIN.name())
                                // All other endpoints require ADMIN authority
                                .anyRequest().hasAuthority(Role.ADMIN.name())
                )
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .addFilterBefore(new OncePerRequestFilter() {
                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                            throws ServletException, IOException {
                        log.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());
                        filterChain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
