package com.myprod.mymessenger.user.manager.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.*;

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
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
