package com.myprod.mymessanger.user.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Collection;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "role")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Role {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Getter
    private Collection<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;
}
