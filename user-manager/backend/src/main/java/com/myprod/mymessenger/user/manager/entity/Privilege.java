package com.myprod.mymessenger.user.manager.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Value
@Builder(toBuilder = true)
@Entity
@Table(
    name = "privilege",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
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

  @Column(nullable = false)
  private Date createdAt = new Date();

  @Override
  public String getAuthority() {
    return name;
  }
}
