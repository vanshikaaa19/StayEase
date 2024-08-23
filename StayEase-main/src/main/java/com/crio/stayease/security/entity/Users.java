package com.crio.stayease.security.entity;

import com.crio.stayease.entity.Booking;
import com.crio.stayease.security.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity representing a user in the system, implementing {@link UserDetails} for Spring Security.
 *
 * <p>This class represents a user in the system with attributes such as first name, last name, email, and password.
 * It also includes user-specific roles and associations with tokens and bookings. The {@link UserDetails} interface
 * methods are implemented to support Spring Security.</p>
 *
 * <p>The user can have different roles defined by the {@link Role} enum, which is used to determine the user's
 * authorities and permissions within the application.</p>
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see com.crio.stayease.security.model.Role
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * First name of the user.
     */
    private String firstname;

    /**
     * Last name of the user.
     */
    private String lastname;

    /**
     * Email address of the user, used as the username for authentication.
     */
    private String email;

    /**
     * Password of the user, used for authentication.
     */
    private String password;

    /**
     * Role of the user, which determines the authorities granted to the user.
     * Default role is {@link Role#CUSTOMER}.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    /**
     * List of tokens associated with the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /**
     * List of bookings made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    /**
     * Returns the authorities granted to the user based on their role.
     * Logs the role of the user for debugging purposes.
     *
     * @return a collection of {@link GrantedAuthority} representing the user's authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    /**
     * Returns the password used for authentication.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used for authentication.
     *
     * @return the email address of the user
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired. Always returns true.
     *
     * @return true, indicating the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked. Always returns true.
     *
     * @return true, indicating the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired. Always returns true.
     *
     * @return true, indicating the credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is enabled. Always returns true.
     *
     * @return true, indicating the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
