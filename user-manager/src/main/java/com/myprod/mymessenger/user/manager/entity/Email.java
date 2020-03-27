package com.myprod.mymessenger.user.manager.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "emails")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Email {
    @Id
    @Column(unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

}