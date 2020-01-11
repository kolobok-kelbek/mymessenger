package com.myprod.mymessenger.user.manager.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone"})
})
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String firstName;

    private String surname;

    private String lastName;

    private String username;

    @Column(nullable = false)
    @NonNull
    private String phone;

    @Column(nullable = false)
    @NonNull
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean accountNonExpired;

    @Column(nullable = false)
    private boolean accountNonLocked;

    @Column(nullable = false)
    private boolean credentialsNonExpired;

    @OneToMany(mappedBy = "user")
    private Set<PhoneNumber> phoneNumbers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.requireNonNull(roles)
                .stream()
                .flatMap(role -> role.getPrivileges().stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
}
