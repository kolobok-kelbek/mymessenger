package com.myprod.mymessanger.user.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "privilege")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Privilege {
    public static final String READ = "READ";
    public static final String WRITE = "WRITE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    @Getter
    private Collection<Role> roles;
}
