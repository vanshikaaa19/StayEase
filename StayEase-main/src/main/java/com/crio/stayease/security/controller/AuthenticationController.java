package com.crio.stayease.security.controller;

import com.crio.stayease.security.model.exchange.AuthenticationRequest;
import com.crio.stayease.security.model.exchange.AuthenticationResponse;
import com.crio.stayease.security.model.exchange.RegisterRequest;
import com.crio.stayease.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller for handling authentication and user registration requests.
 * <p>
 * This controller provides endpoints for user registration, login, and token refresh operations.
 * It interacts with the {@link AuthenticationService} to perform authentication-related operations.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Handles user registration requests.
     * <p>
     * This endpoint accepts a {@link RegisterRequest} object containing user registration details
     * and returns an {@link AuthenticationResponse} containing authentication information such as tokens.
     * </p>
     *
     * @param request the registration request containing user details
     * @return a {@link ResponseEntity} containing the authentication response with status OK
     */
    @Operation(
            summary = "Register a new user",
            description = "Handles user registration requests and returns authentication details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration details",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RegisterRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully registered the user"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid registration details"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Handles user login requests.
     * <p>
     * This endpoint accepts an {@link AuthenticationRequest} object containing user login credentials
     * and returns an {@link AuthenticationResponse} containing authentication information such as tokens.
     * </p>
     *
     * @param request the login request containing user credentials
     * @return a {@link ResponseEntity} containing the authentication response with status OK
     */
    @Operation(
            summary = "User login",
            description = "Handles user login requests and returns authentication details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login credentials",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully logged in"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized, invalid credentials"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Handles token refresh requests.
     * <p>
     * This endpoint processes token refresh requests by extracting the token from the request,
     * validating it, and issuing a new token if the old one is valid. The response is written directly
     * to the {@link HttpServletResponse} object.
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing the current token
     * @param response the {@link HttpServletResponse} to write the new token to
     * @throws IOException if an I/O error occurs while writing the response
     */
    @Operation(
            summary = "Refresh authentication token",
            description = "Handles token refresh requests and issues a new token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully refreshed the token"),
                    @ApiResponse(responseCode = "400", description = "Bad request, invalid token"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized, invalid token"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
}
