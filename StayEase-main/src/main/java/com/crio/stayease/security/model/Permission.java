package com.crio.stayease.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing various permissions for users within the system.
 *
 * <p>This enum defines a set of permissions that can be assigned to users. Each permission represents
 * a specific level of access or action that can be performed within the application. The permissions
 * are categorized based on roles such as ADMIN, MANAGER, and CUSTOMER, and include both read and write
 * capabilities.</p>
 *
 * @see com.crio.stayease.security.model.Role
 */
@Getter
@RequiredArgsConstructor
public enum Permission {

    /**
     * Permission to read administrative data.
     */
    ADMIN_READ("admin:read"),

    /**
     * Permission to write or modify administrative data.
     */
    ADMIN_WRITE("admin:write"),

    /**
     * Permission to read management-related data.
     */
    MANAGER_READ("management:read"),

    /**
     * Permission to write or modify management-related data.
     */
    MANAGER_WRITE("management:write"),

    /**
     * Permission to read customer-related data.
     */
    CUSTOMER_READ("customer:read"),

    /**
     * Permission to write or modify customer-related data.
     */
    CUSTOMER_WRITE("customer:write");

    /**
     * The string representation of the permission.
     */
    private final String permission;
}
