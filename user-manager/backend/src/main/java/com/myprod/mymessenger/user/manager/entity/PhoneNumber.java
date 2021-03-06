package com.myprod.mymessenger.user.manager.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Entity
@Table(name = "phone_numbers")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PhoneNumber {
  @Id
  @Column(unique = true, updatable = false)
  private String number;

  @Column(nullable = false)
  private Date createdAt = new Date();

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
