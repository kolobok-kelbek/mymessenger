package com.myprod.mymessanger.user.manager.service;

import com.myprod.mymessanger.user.manager.entity.PhoneNumber;
import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessanger.user.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class UserService {

  private UserRepository userRepository;
  private PhoneNumberRepository phoneNumberRepository;

  @Autowired
  public UserService(UserRepository userRepository, PhoneNumberRepository phoneNumber) {
    this.userRepository = userRepository;
    this.phoneNumberRepository = phoneNumber;
  }

  public User findUser(UUID uuid) {
    return userRepository.findByUuid(uuid);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }

  public Page<User> findLimitUsers(int limit, int offset) {

    if (limit <= 0) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid value of limit");
    }

    if (offset < 0) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid value of offset");
    }

    Pageable pageable = PageRequest.of(offset, limit);

    return userRepository.findAll(pageable);
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

}
