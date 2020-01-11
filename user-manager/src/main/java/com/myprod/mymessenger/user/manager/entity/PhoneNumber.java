package com.myprod.mymessenger.user.manager.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "phone_numbers")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PhoneNumber {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(unique = true)
    private String number;

    @Column
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


}
