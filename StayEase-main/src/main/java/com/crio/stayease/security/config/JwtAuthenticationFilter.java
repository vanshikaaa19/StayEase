package com.crio.stayease.security.config;

import com.crio.stayease.security.repository.TokenRepository;
import com.crio.stayease.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for validating JWT tokens and setting the authentication in the security context.
 *
 * <p>This filter extracts the JWT token from the Authorization header of the HTTP request, validates
 * the token, and sets the user authentication in the Spring Security context if the token is valid.
 * It processes each request to ensure that the security context is appropriately updated based on
 * the provided JWT token.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    /**
     * Extracts the JWT token from the request header, validates it, and sets the authentication in
     * the security context.
     *
     * <p>This method performs the following steps:
     * <ul>
     *     <li>Checks if the request path is related to authentication and skips the filter if it is.</li>
     *     <li>Extracts the JWT token from the Authorization header if it exists and starts with "Bearer ".</li>
     *     <li>Extracts the username from the JWT token and loads the user details from the database.</li>
     *     <li>Checks if the token is valid and not expired or revoked.</li>
     *     <li>Sets the authentication in the security context if the token is valid and properly authenticated.</li>
     * </ul></p>
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to pass the request and response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        log.debug("Processing request to: {}", request.getRequestURI());

        // Skip filter for authentication endpoints
        if (request.getServletPath().contains("/api/v1/auth")) {
            log.debug("Skipping JWT filter for auth endpoint");
            filterChain.doFilter(request, response);
            return;
        }

        // Check for the presence of the JWT token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        log.debug("JWT token extracted from header");

        try {
            userEmail = jwtService.extractUsername(jwt);
            log.debug("Extracted username from JWT: {}", userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                log.debug("Loaded UserDetails for {}: {}", userEmail, userDetails);

                boolean isTokenValid = tokenRepository.findByToken(jwt)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                log.debug("Is token valid: {}", isTokenValid);

                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authentication set in SecurityContext for user: {}", userEmail);
                    log.debug("User authorities: {}", userDetails.getAuthorities());
                } else {
                    log.warn("Token validation failed for user: {}", userEmail);
                }
            }
        } catch (Exception e) {
            log.error("Error processing JWT token", e);
        }

        filterChain.doFilter(request, response);
    }
}
