package com.crio.stayease.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The {@code CustomAccessDeniedHandler} class implements {@code AccessDeniedHandler} to handle access denied errors in a
 * customized manner.
 *
 * <p>This class is used to provide a custom response when a user attempts to access a resource for which they do not have
 * the necessary permissions. It sets an HTTP 403 Forbidden status and returns a JSON error message.</p>
 *
 * <p><b>Implementation:</b></p>
 * <ul>
 *   <li>{@code handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)}
 *       - This method is called when an {@code AccessDeniedException} is thrown. It sets the response status to 403 (Forbidden),
 *       sets the content type to JSON, and writes an error message to the response body.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This handler can be configured in the Spring Security configuration to replace the default access denied handler. It is
 * useful for providing a more user-friendly error message or logging additional information about access denial.</p>
 *
 * <pre>{@code
 * // Example configuration in Spring Security:
 * @Configuration
 * public class SecurityConfig extends WebSecurityConfigurerAdapter {
 *     @Autowired
 *     private CustomAccessDeniedHandler customAccessDeniedHandler;
 *
 *     @Override
 *     protected void configure(HttpSecurity http) throws Exception {
 *         http
 *             .exceptionHandling()
 *             .accessDeniedHandler(customAccessDeniedHandler);
 *     }
 * }
 * }</pre>
 *
 * @see org.springframework.security.access.AccessDeniedException
 * @see org.springframework.security.web.access.AccessDeniedHandler
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denied error by setting the response status to 403 Forbidden and returning a JSON error message.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @param accessDeniedException the exception that was thrown due to access denial
     * @throws IOException if an input or output error occurs while handling the response
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Access Denied: You do not have the necessary permissions to access this resource.\"}");
    }
}
