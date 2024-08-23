package com.crio.stayease.security.model.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request for authentication containing the user's credentials.
 *
 * <p>This class is used to encapsulate the authentication data sent by a user when attempting to
 * log in or authenticate. It typically includes the user's email address and password.</p>
 *
 * <p>The {@code AuthenticationRequest} class provides the necessary fields for authentication purposes:
 * <ul>
 *     <li>{@code email} - The email address of the user attempting to authenticate.</li>
 *     <li>{@code password} - The password of the user attempting to authenticate.</li>
 * </ul>
 * </p>
 *
 * <p>Note: This class is commonly used in authentication services to validate user credentials and
 * generate access tokens or other forms of authentication tokens upon successful login.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The password of the user attempting to authenticate.
     */
    private String password;

    /**
     * The email address of the user attempting to authenticate.
     */
    private String email;
}
