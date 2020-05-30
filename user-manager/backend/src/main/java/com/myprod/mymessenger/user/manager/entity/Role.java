package com.myprod.mymessenger.user.manager.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Value
@Builder(toBuilder = true)
@Entity
@Table(
    name = "role",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Role implements Serializable {
  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private Date createdAt = new Date();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_privileges",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  private Collection<Privilege> privileges;
}
