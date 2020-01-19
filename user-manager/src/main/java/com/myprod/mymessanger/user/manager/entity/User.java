package com.myprod.mymessanger.user.manager.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.*;
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
    private final UUID uuid;

    @Column(name = "name")
    private final String name;

    @Column(name = "surname")
    private final String surname;

    @Column(name = "last_name")
    private final String lastName;

    @Column(name = "username")
    private final String username;

}
