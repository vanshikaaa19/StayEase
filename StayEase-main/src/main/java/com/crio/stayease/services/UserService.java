package com.crio.stayease.services;

import com.crio.stayease.security.entity.Users;

import java.util.List;

/**
 * The {@code UserService} interface defines the contract for services related to user management.
 *
 * <p>This interface provides methods for retrieving user information, including getting details of a specific user,
 * getting the current user's details, and retrieving a list of all users.</p>
 *
 * <p><b>Methods:</b></p>
 * <ul>
 *   <li>{@code Users getUserById(Long id)}
 *       - Retrieves a user based on the provided ID. Returns the {@code Users} object representing the user.</li>
 *   <li>{@code Users aboutMe()}
 *       - Retrieves details of the currently authenticated user. Returns the {@code Users} object representing the current user.</li>
 *   <li>{@code List<Users> getUsers()}
 *       - Retrieves a list of all users. Returns a list of {@code Users} objects.</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <p>This interface should be implemented to provide business logic for managing user data. It can be used by controllers
 * or other services to interact with user data and perform various user-related operations.</p>
 *
 * <pre>{@code
 * @Autowired
 * private UserService userService;
 *
 * // Example usage:
 * Users user = userService.getUserById(1L);
 * Users currentUser = userService.aboutMe();
 * List<Users> allUsers = userService.getUsers();
 * }</pre>
 *
 * @see com.crio.stayease.security.entity.Users
 */
public interface UserService {

    /**
     * Retrieves a user based on the provided ID.
     *
     * @param id the ID of the user to retrieve
     * @return the {@code Users} object representing the user with the specified ID
     */
    Users getUserById(Long id);

    /**
     * Retrieves details of the currently authenticated user.
     *
     * @return the {@code Users} object representing the currently authenticated user
     */
    Users aboutMe();

    /**
     * Retrieves a list of all users.
     *
     * @return a list of all {@code Users} objects
     */
    List<Users> getUsers();
}
