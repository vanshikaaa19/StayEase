package com.crio.stayease.security.auditing;

import com.crio.stayease.security.entity.Users;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} for providing the current auditor's ID.
 *
 * <p>This class retrieves the current authenticated user's ID from the security context to be used for auditing purposes.
 * If the current user is not authenticated or is an anonymous user, it returns an empty {@link Optional}.</p>
 *
 * @see org.springframework.data.domain.AuditorAware
 * @see com.crio.stayease.security.entity.Users
 */
public class ApplicationAuditAware implements AuditorAware<Integer> {

    /**
     * Retrieves the ID of the current authenticated user to be used as the auditor.
     *
     * <p>This method checks the security context for the current authentication. If the user is authenticated and not
     * an anonymous user, their ID is returned. Otherwise, an empty {@link Optional} is returned.</p>
     *
     * @return an {@link Optional} containing the ID of the current authenticated user, or an empty {@link Optional}
     *         if the user is not authenticated or is an anonymous user
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        Users userPrincipal = (Users) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}
