package com.myprod.mymessenger.user.manager.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "privilege", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Privilege implements GrantedAuthority, Serializable {
    public static final String READ = "READ";
    public static final String WRITE = "WRITE";

    public static final String READ_USER = "READ_USER";
    public static final String WRITE_USER = "WRITE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
