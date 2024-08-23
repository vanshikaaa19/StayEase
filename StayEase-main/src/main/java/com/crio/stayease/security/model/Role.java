package com.crio.stayease.security.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum representing different user roles and their associated permissions in the system.
 *
 * <p>This enum defines various roles within the application, each with a specific set of permissions.
 * The roles are categorized as ADMIN, MANAGER, and CUSTOMER, and each role is associated with one or
 * more permissions that define what actions the role can perform.</p>
 *
 * <p>Each role provides a list of authorities that include both the role name and its permissions,
 * which are used by Spring Security for authorization checks.</p>
 *
 * @see Permission
 */
@Getter
public enum Role {

    /**
     * Role with administrative privileges, including both read and write access.
     */
    ADMIN(Set.of(Permission.ADMIN_READ, Permission.ADMIN_WRITE)),

    /**
     * Role with management privileges, including both read and write access.
     */
    MANAGER(Set.of(Permission.MANAGER_READ, Permission.MANAGER_WRITE)),

    /**
     * Role with customer privileges, including read access.
     */
    CUSTOMER(Set.of(Permission.CUSTOMER_READ));

    /**
     * The set of permissions associated with this role.
     */
    private final Set<Permission> permissions;

    /**
     * Constructs a new {@code Role} instance with the specified set of permissions.
     *
     * @param permissions the set of permissions associated with this role
     */
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Returns a list of authorities granted to this role. Authorities include both the role name and
     * its associated permissions.
     *
     * <p>This method converts the set of permissions into {@link SimpleGrantedAuthority} instances
     * and adds the role name as an authority. This list of authorities is used by Spring Security to
     * perform authorization checks.</p>
     *
     * @return a list of {@link SimpleGrantedAuthority} instances representing the role's authorities
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
