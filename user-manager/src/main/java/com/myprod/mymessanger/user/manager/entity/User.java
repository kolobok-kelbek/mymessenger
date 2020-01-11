package com.myprod.mymessanger.user.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class User {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String name;

    private String surname;

    private String lastName;

    private String nickname;

    private String email;

    private String password;

    private boolean enabled;

    private boolean tokenExpired;

    @OneToMany(mappedBy = "user")
    private Set<PhoneNumber> phoneNumbers;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}

