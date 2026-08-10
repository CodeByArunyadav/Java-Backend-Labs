package com.hoxcloud.lab.config;
import com.hoxcloud.lab.entity.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public class PermissionMapping {

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Roles role) {

        return switch (role) {

            case ADMIN -> Set.of(
                    new SimpleGrantedAuthority("READ_USER"),
                    new SimpleGrantedAuthority("WRITE_USER"),
                    new SimpleGrantedAuthority("DELETE_USER")
            );

            case CREATOR -> Set.of(
                    new SimpleGrantedAuthority("READ_USER"),
                    new SimpleGrantedAuthority("WRITE_USER")
            );

            case USER -> Set.of(
                    new SimpleGrantedAuthority("READ_USER")
            );
        };
    }
}