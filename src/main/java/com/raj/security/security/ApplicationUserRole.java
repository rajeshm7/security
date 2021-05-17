package com.raj.security.security;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import static com.raj.security.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_WRITE, STUDENT_READ, ADMIN_READ, ADMIN_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
}
