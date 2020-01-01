package com.myprod.mymessanger.user.manager.controller;

import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
}
