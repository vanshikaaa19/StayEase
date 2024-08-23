package com.crio.stayease.controller;

import com.crio.stayease.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user-related operations in the StayEase application.
 * <p>
 * This controller provides endpoints for accessing user information, including:
 * <ul>
 *   <li>Retrieving a list of all users.</li>
 *   <li>Retrieving information about the currently authenticated user.</li>
 *   <li>Retrieving a specific user by their ID.</li>
 * </ul>
 * </p>
 *
 * The controller uses the {@link UserService} to handle the business logic for each operation.
 *
 * @see UserService
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a list of all users.
     *
     * @return A {@link ResponseEntity} containing a list of all users.
     */
    @Operation(
            summary = "Retrieve all users",
            description = "Retrieve a list of all users in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return A {@link ResponseEntity} containing details about the current user.
     */
    @Operation(
            summary = "Retrieve current user information",
            description = "Retrieve details about the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved current user information"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("me")
    public ResponseEntity<?> aboutMe() {
        return ResponseEntity.ok(userService.aboutMe());
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link ResponseEntity} containing the details of the specified user.
     */
    @Operation(
            summary = "Retrieve user by ID",
            description = "Retrieve details of a specific user by their ID.",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to retrieve", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved user by ID"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
