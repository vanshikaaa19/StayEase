package com.crio.stayease.security.model.exchange;

import com.crio.stayease.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to register a new user.
 *
 * <p>This class encapsulates the information required to register a new user in the system,
 * including personal details and role assignments.</p>
 *
 * <p>The {@code RegisterRequest} class contains the following fields:
 * <ul>
 *     <li>{@code firstname} - The user's first name.</li>
 *     <li>{@code lastname} - The user's last name.</li>
 *     <li>{@code email} - The user's email address, which will be used as their username for authentication.</li>
 *     <li>{@code password} - The password chosen by the user, which will be used for authentication.</li>
 *     <li>{@code role} - The role assigned to the user, which determines their permissions within the system.</li>
 * </ul>
 * </p>
 *
 * <p>Note: The registration process typically involves validating these fields to ensure that
 * the email is unique, the password meets security requirements, and the role is valid.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The user's first name.
     */
    private String firstname;

    /**
     * The user's last name.
     */
    private String lastname;

    /**
     * The user's email address, which will be used as their username for authentication.
     */
    private String email;

    /**
     * The password chosen by the user, which will be used for authentication.
     */
    private String password;

    /**
     * The role assigned to the user, which determines their permissions within the system.
     */
    private Role role;
}
