package com.myprod.mymessenger.user.manager.entity;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"phone"})})
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class User implements UserDetails {
  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  private String firstName;

  private String surname;

  private String lastName;

  private String username;

  @Column(nullable = false)
  @NonNull
  private String phone;

  @Column(nullable = false)
  @NonNull
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private boolean accountNonExpired;

  @Column(nullable = false)
  private boolean accountNonLocked;

  @Column(nullable = false)
  private boolean credentialsNonExpired;

  @OneToMany(mappedBy = "user")
  private Set<PhoneNumber> phoneNumbers;

  @OneToMany(mappedBy = "user")
  private Set<Email> emails;

  private Date updatedAt;

  @Column(nullable = false)
  private Date createdAt = new Date();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Objects.requireNonNull(roles)
        .stream()
        .flatMap(role -> role.getPrivileges().stream())
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }
}
