package com.crio.stayease.services.implementation;

import com.crio.stayease.security.entity.Users;
import com.crio.stayease.security.repository.UserRepository;
import com.crio.stayease.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Implementation of the {@link UserService} interface for managing user-related operations.
 *
 * <p>This service provides operations for retrieving user details, including getting user information by ID,
 * retrieving the current authenticated user, and listing all users.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@code UserRepository} - Repository for user management.</li>
 * </ul>
 *
 * @see com.crio.stayease.services.UserService
 * @see com.crio.stayease.security.entity.Users
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@link Users} object associated with the given ID
     * @throws ResponseStatusException if the user is not found
     */
    @Override
    public Users getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @return the {@link Users} object of the currently authenticated user
     * @throws ResponseStatusException if the user is not found
     */
    @Override
    public Users aboutMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return repository.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of {@link Users} objects representing all users in the system
     */
    @Override
    public List<Users> getUsers() {
        return repository.findAll();
    }
}
