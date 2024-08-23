package com.crio.stayease.security.entity;

import com.crio.stayease.security.model.TokenType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an authentication or authorization token.
 *
 * <p>This class represents a token issued to users for authentication or authorization purposes.
 * It includes information about the token itself, its type, and its validity status. The token is
 * associated with a specific user, and its type can be specified as either BEARER or another type as defined
 * by the {@link TokenType} enum.</p>
 *
 * @see com.crio.stayease.security.model.TokenType
 * @see com.crio.stayease.security.entity.Users
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    /**
     * Unique identifier for the token.
     */
    @Id
    @GeneratedValue
    public Integer id;

    /**
     * The actual token string.
     * Must be unique across all tokens.
     */
    @Column(unique = true)
    public String token;

    /**
     * The type of the token, indicating how it should be used.
     * Default is {@link TokenType#BEARER}.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    /**
     * Indicates whether the token has been revoked and should no longer be used.
     */
    public boolean revoked;

    /**
     * Indicates whether the token has expired and is no longer valid.
     */
    public boolean expired;

    /**
     * The user associated with this token.
     * The relationship is many-to-one, meaning a user can have multiple tokens.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    public Users user;
}
