package com.myprod.mymessanger.user.manager.entity;

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
  private UUID uuid;

  @Column(unique = true)
  private String number;

  @Column
  private Date createAt;

  @ManyToOne
  @JoinColumn(name = "users_id", referencedColumnName = "uuid")
  private User user;


}
