package com.crio.stayease.security.service;

import com.crio.stayease.security.entity.Token;
import com.crio.stayease.security.entity.Users;
import com.crio.stayease.security.model.TokenType;
import com.crio.stayease.security.model.exchange.AuthenticationRequest;
import com.crio.stayease.security.model.exchange.AuthenticationResponse;
import com.crio.stayease.security.model.exchange.ChangePasswordRequest;
import com.crio.stayease.security.model.exchange.RegisterRequest;
import com.crio.stayease.security.repository.TokenRepository;
import com.crio.stayease.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;

/**
 * Service class for handling authentication-related operations such as registration, login, password change, and token management.
 *
 * <p>This service handles the following functionalities:</p>
 * <ul>
 *     <li>User registration and token generation</li>
 *     <li>User authentication and token management</li>
 *     <li>Token refresh mechanism</li>
 *     <li>Password change operation</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user and generates JWT tokens for the user.
     *
     * <p>This method saves the new user details to the database, generates an access token and a refresh token,
     * and returns these tokens in the response.</p>
     *
     * @param request the registration request containing user details
     * @return an {@link AuthenticationResponse} containing the access and refresh tokens
     */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Users.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Authenticates a user and generates new JWT tokens.
     *
     * <p>This method authenticates the user based on the provided credentials, generates a new access token and refresh token,
     * revokes all previous tokens for the user, and saves the new token.</p>
     *
     * @param request the authentication request containing email and password
     * @return an {@link AuthenticationResponse} containing the access and refresh tokens
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Saves a new token for a user in the database.
     *
     * <p>This method creates a new {@link Token} entity with the provided JWT token and associates it with the specified user.</p>
     *
     * @param user the user for whom the token is being saved
     * @param jwtToken the JWT token to be saved
     */
    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Revokes all valid tokens for a user.
     *
     * <p>This method sets the tokens associated with the user as expired and revoked to ensure the old tokens are no longer valid.</p>
     *
     * @param user the user for whom all tokens should be revoked
     */
    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Refreshes the access token using the provided refresh token.
     *
     * <p>This method extracts the refresh token from the request header, validates it, generates a new access token,
     * revokes the old tokens, and returns the new tokens in the response.</p>
     *
     * @param request the HTTP request containing the authorization header with the refresh token
     * @param response the HTTP response to write the new tokens to
     * @throws IOException if an I/O error occurs while writing the response
     */
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    /**
     * Changes the user's password.
     *
     * <p>This method validates the current password, checks if the new password and confirmation password match,
     * and updates the user's password if all validations pass.</p>
     *
     * @param request the request containing current and new password details
     * @param connectedUser the currently authenticated user whose password is being changed
     * @throws IllegalStateException if the current password is incorrect or new passwords do not match
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (Users) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // save the new password
        repository.save(user);
    }
}
