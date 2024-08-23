package com.crio.stayease.security.repository;

import com.crio.stayease.security.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link Users} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and custom queries
 * related to {@link Users} entities.</p>
 *
 * <p>Provides the following custom methods:</p>
 * <ul>
 *     <li>{@link #findByEmail(String)} - Retrieves a user based on their email address.</li>
 * </ul>
 */
public interface UserRepository extends JpaRepository<Users, Long> {

    /**
     * Finds a user by their email address.
     *
     * <p>This method returns an optional {@link Users} object that matches the given email address.
     * If no user with the specified email is found, an empty optional is returned.</p>
     *
     * @param email the email address of the user to be retrieved
     * @return an optional containing the user if found, or an empty optional if no user is found
     */
    Optional<Users> findByEmail(String email);
}
