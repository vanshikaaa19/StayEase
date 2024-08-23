package com.crio.stayease.security.service;

import com.crio.stayease.security.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user logout operations.
 *
 * <p>This service implements the {@link LogoutHandler} interface to manage the logout process, which includes
 * invalidating the JWT token and clearing the security context.</p>
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    /**
     * Handles the logout process by invalidating the JWT token and clearing the security context.
     *
     * <p>This method extracts the JWT token from the request header, marks it as expired and revoked, and
     * saves the updated token state in the database. It then clears the security context to ensure that
     * the user is logged out.</p>
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param authentication the authentication object representing the current user
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
