package com.hoxcloud.lab.entity;

import com.hoxcloud.lab.config.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,nullable = false)
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String userName;
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public UserEntity(long l, String mail, String number) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> {

            // Add permissions
            authorities.addAll(
                    PermissionMapping.getAuthoritiesForRole(role)
            );

            // Add role
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + role.name())
            );
        });
        System.out.println("AUTHORITIES = " + authorities);
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
