package com.myprod.mymessanger.user.manager.controller;

import com.myprod.mymessanger.user.manager.dto.BatchDTO;
import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
}
