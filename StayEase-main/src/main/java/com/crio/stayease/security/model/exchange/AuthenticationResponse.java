package com.crio.stayease.security.model.exchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a response containing authentication tokens.
 *
 * <p>This class is used to encapsulate the authentication tokens returned to the user after a successful
 * login or authentication request. It includes an access token and a refresh token, which are used to
 * manage user sessions and maintain authentication state.</p>
 *
 * <p>The {@code AuthenticationResponse} class contains the following fields:
 * <ul>
 *     <li>{@code accessToken} - The access token issued to the user upon successful authentication.
 *         This token is typically used to access protected resources and APIs.</li>
 *     <li>{@code refreshToken} - The refresh token issued to the user to obtain a new access token
 *         when the current access token expires. The refresh token allows the user to stay authenticated
 *         without needing to log in again.</li>
 * </ul>
 * </p>
 *
 * <p>Note: The tokens included in this response are usually JSON Web Tokens (JWTs) or similar token types
 * used for secure authentication and authorization.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The access token issued to the user upon successful authentication.
     * This token is used to access protected resources and APIs.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * The refresh token issued to the user to obtain a new access token
     * when the current access token expires.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
