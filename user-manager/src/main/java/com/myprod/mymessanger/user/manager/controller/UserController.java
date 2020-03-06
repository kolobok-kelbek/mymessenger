package com.myprod.mymessanger.user.manager.controller;

import com.myprod.mymessanger.user.manager.dto.BatchDTO;
import com.myprod.mymessanger.user.manager.dto.EmailDTO;
import com.myprod.mymessanger.user.manager.dto.PhoneDTO;
import com.myprod.mymessanger.user.manager.entity.Email;
import com.myprod.mymessanger.user.manager.entity.PhoneNumber;
import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
final public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/{uuid}")
  public User getUser(@PathVariable UUID uuid) {
    return userService.findUser(uuid);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    userService.saveUser(user);

    return user;
  }

  @PutMapping
  public User updateUser(@RequestBody User user) {
    userService.updateUser(user);

    return user;
  }

  @GetMapping
  public BatchDTO<User> getLimitUsers(@RequestParam Integer limit, @RequestParam Integer offset) {
    Page<User> page;

    if (null == limit) {
      limit = 10;
    }

    if (null == offset) {
      offset = 0;
    }

    try {
      page = userService.findLimitUsers(limit, offset);
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return BatchDTO.<User>builder()
      .limit(limit)
      .offset(offset)
      .number(userService.getCount())
      .list(page.toList())
      .build();
  }

  @PostMapping("/{uuid}/phone")
  public PhoneDTO createPhone(@PathVariable UUID uuid, @RequestBody PhoneNumber number) {
    User user = userService.findUser(uuid);
    PhoneNumber crePhoneNumber = PhoneNumber.builder()
      .user(user)
      .createAt(new Date())
      .number(number.getNumber())
      .build();
    PhoneNumber phoneNumber = userService.addPhoneNumber(crePhoneNumber);

    return PhoneDTO.builder()
      .number(phoneNumber.getNumber())
      .createAt(phoneNumber.getCreateAt())
      .build();

  }

  @PostMapping("/{uuid}/phone/{phone_uuid}")
  public void deletePhone(@PathVariable UUID uuid, @PathVariable PhoneNumber phone_uuid) {
    User user = userService.findUser(uuid);
    PhoneNumber delPhoneNumber = PhoneNumber.builder()
      .user(user)
      .uuid(phone_uuid.getUuid())
      .createAt(phone_uuid.getCreateAt())
      .number(phone_uuid.getNumber())
      .build();
    PhoneNumber phoneNumber = userService.addPhoneNumber(delPhoneNumber);

    userService.deletePhoneNumber(phoneNumber);

  }

  @PostMapping("/{uuid}/email")
  public EmailDTO createEmail(@PathVariable UUID uuid, @RequestBody Email email){
    User user = userService.findUser(uuid);
    Email addEmail = Email.builder()
      .user(user)
      .email(email.getEmail())
      .createAt(new Date())
      .build();
    Email addedEmail = userService.addEmail(addEmail);
    return EmailDTO.builder()
      .email(addedEmail.getEmail())
      .createAt(addedEmail.getCreateAt())
      .build();
  }
}
