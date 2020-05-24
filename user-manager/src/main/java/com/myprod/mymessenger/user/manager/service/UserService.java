package com.myprod.mymessenger.user.manager.service;

import com.myprod.mymessenger.user.manager.entity.*;
import com.myprod.mymessenger.user.manager.exception.ChangePasswordException;
import com.myprod.mymessenger.user.manager.model.input.PaginationQuery;
import com.myprod.mymessenger.user.manager.repository.EmailRepository;
import com.myprod.mymessenger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessenger.user.manager.repository.RoleRepository;
import com.myprod.mymessenger.user.manager.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsManager {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PhoneNumberRepository phoneNumberRepository;
  private EmailRepository emailRepository;

  @Autowired
  public UserService(
      UserRepository userRepository,
      PhoneNumberRepository phoneNumberRepository,
      RoleRepository roleRepository,
      EmailRepository emailRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.phoneNumberRepository = phoneNumberRepository;
    this.emailRepository = emailRepository;
  }

  public User getCurrentUser() {
    String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return findUserByPhone(phone);
  }

  public User findUser(UUID id) {
    return userRepository.findById(id).orElse(null);
  }

  public User findUserByPhone(String Phone) {
    return userRepository.findByPhone(Phone).orElse(null);
  }

  public Page<User> findLimitUsers(PaginationQuery paginationQuery) {
    return userRepository.findAll(
        PageRequest.of(paginationQuery.getOffset(), paginationQuery.getLimit()));
  }

  public Role findRole(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

    User user = userRepository.findByPhone(phone).orElse(null);

    if (null == user) {
      return new org.springframework.security.core.userdetails.User(
          " ",
          " ",
          true,
          true,
          true,
          true,
          getAuthorities(Collections.singletonList(roleRepository.findByName(Role.USER))));
    }

    return new org.springframework.security.core.userdetails.User(
        user.getPhone(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities(user.getRoles()));
  }

  public void createUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void createUser(UserDetails user) {
    createUser((User) user);
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void updateUser(UserDetails user) {
    updateUser((User) user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  @Override
  public void deleteUser(String username) {
    deleteUser(userRepository.findByUsername(username).orElseThrow());
  }

  @Override
  @SneakyThrows
  public void changePassword(String oldPassword, String newPassword) {
    if (oldPassword.equals(newPassword)) {
      throw new ChangePasswordException("Old password equals new password");
    }
    if (newPassword.isEmpty()) {
      throw new ChangePasswordException("New password can't be empty");
    }
    if (oldPassword.isEmpty()) {
      throw new ChangePasswordException("Password can't be empty");
    }
    throw new ChangePasswordException("This functionality is not ready yet");
  }

  @Override
  public boolean userExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean userExistsByPhone(String phone) {
    return userRepository.findByPhone(phone).isPresent();
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<String> getPrivileges(Collection<Role> roles) {

    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();

    for (Role role : roles) {
      collection.addAll(role.getPrivileges());
    }

    for (Privilege item : collection) {
      privileges.add(item.getName());
    }

    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }

    return authorities;
  }

  public long getCount() {
    return userRepository.count();
  }

  public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber) {
    return phoneNumberRepository.save(phoneNumber);
  }

  public void deletePhoneNumber(PhoneNumber phoneNumber) {
    phoneNumberRepository.delete(phoneNumber);
  }

  public Email addEmail(Email email) {
    return emailRepository.save(email);
  }

  public void deleteEmail(Email email) {
    emailRepository.delete(email);
  }

  public Set<String> getEmailsCurrentUser() {
    return getCurrentUser().getEmails().stream().map(Email::getEmail).collect(Collectors.toSet());
  }

  public Set<String> getPhoneNumbersCurrentUser() {
    return getCurrentUser()
        .getPhoneNumbers()
        .stream()
        .map(PhoneNumber::getNumber)
        .collect(Collectors.toSet());
  }
}
