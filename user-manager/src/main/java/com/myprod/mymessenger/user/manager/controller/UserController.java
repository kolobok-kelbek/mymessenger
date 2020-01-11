package com.myprod.mymessenger.user.manager.controller;

import com.myprod.mymessenger.user.manager.converter.dto.ListViewConverter;
import com.myprod.mymessenger.user.manager.converter.dto.UserConverter;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.request.UserRequest;
import com.myprod.mymessenger.user.manager.model.view.ListView;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import com.myprod.mymessenger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    private final UserConverter userAssembler;

    @Autowired
    public UserController(UserService userService, UserConverter userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public UserView getCurrentUser() {
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userAssembler.convertEntity(userService.findUserByPhone(phone));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user")
    public User updateCurrentUser(@RequestBody UserRequest userRequest) {
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.findUserByPhone(phone);

        userService.updateUser(user);

        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{uuid}")
    public UserView getUser(@PathVariable UUID uuid) {
        return userAssembler.convertEntity(userService.findUser(uuid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    public UserView createUser(@RequestBody User user) {
        userService.createUser(user);

        return userAssembler.convertEntity(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users")
    public UserView updateUser(@RequestBody User user) {
        userService.updateUser(user);

        return userAssembler.convertEntity(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ListView<UserView> getLimitUsers(@RequestParam int limit, @RequestParam int offset) {
        try {
            userService.findLimitUsers(limit, offset);
        } catch (IllegalArgumentException e) {
            System.out.println("\"Limit\" cannot be less than or equal to zero and \"offset\" cannot be less than zero");
        }

        Page<User> page = userService.findLimitUsers(limit, offset);

        return ListViewConverter.getListViewAssembler(userAssembler).convertEntity(page);
    }
}
