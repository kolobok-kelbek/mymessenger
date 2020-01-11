package com.myprod.mymessenger.user.manager.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "email")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Email {
    @Id
    @GeneratedValue
    private final UUID id;

}
