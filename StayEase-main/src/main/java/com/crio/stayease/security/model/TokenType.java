package com.crio.stayease.security.model;

/**
 * Enum representing the type of authentication token used in the system.
 *
 * <p>This enum defines the various types of tokens that can be used for authentication
 * in the application. Currently, only the BEARER token type is supported.</p>
 *
 * <p>Authentication tokens are used to verify the identity of a user or system,
 * and the type of token determines how the token should be processed and validated.</p>
 */
public enum TokenType {

    /**
     * Represents a bearer token type used in authentication.
     *
     * <p>A bearer token is a type of access token that allows the bearer to access resources
     * without needing to present any additional credentials. It is included in the HTTP
     * Authorization header as a `Bearer` token.</p>
     */
    BEARER
}
