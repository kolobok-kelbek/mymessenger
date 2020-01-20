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
@Table(name = "phone_number")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Phone_Nubmer {

    @Id
    @GeneratedValue
    private final UUID uuid;

    @Column
    private final String number;

}
