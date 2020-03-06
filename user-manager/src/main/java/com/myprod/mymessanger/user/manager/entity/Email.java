package com.myprod.mymessanger.user.manager.entity;

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
  @GeneratedValue
  private UUID uuid;

  @Column(unique = true)
  private String email;

  @Column
  private Date createAt;

  @ManyToOne
  @JoinColumn(name = "users_id", referencedColumnName = "uuid")
  private User user;

}
