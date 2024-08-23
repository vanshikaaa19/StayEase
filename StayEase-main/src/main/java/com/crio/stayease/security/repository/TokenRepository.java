package com.crio.stayease.security.repository;

import com.crio.stayease.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link Token} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and custom queries
 * related to {@link Token} entities.</p>
 *
 * <p>Provides the following custom methods:</p>
 * <ul>
 *     <li>{@link #findAllValidTokenByUser(Integer)} - Retrieves all valid tokens associated with a user.</li>
 *     <li>{@link #findByToken(String)} - Retrieves a token by its value.</li>
 * </ul>
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {

    /**
     * Finds all valid tokens for a specified user.
     *
     * <p>This method returns a list of tokens that are associated with a user and are neither expired
     * nor revoked. Tokens are considered valid if they are not marked as expired or revoked.</p>
     *
     * @param id the ID of the user whose tokens are to be retrieved
     * @return a list of valid tokens for the specified user
     */
    @Query(value = """
            select t from Token t
            inner join Users u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Integer id);

    /**
     * Finds a token by its value.
     *
     * <p>This method returns an optional token that matches the given token value. If no token is found,
     * an empty optional is returned.</p>
     *
     * @param token the value of the token to be retrieved
     * @return an optional containing the token if found, or an empty optional if not found
     */
    Optional<Token> findByToken(String token);
}
