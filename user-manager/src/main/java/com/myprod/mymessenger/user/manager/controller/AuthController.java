package com.myprod.mymessenger.user.manager.controller;

import com.myprod.mymessenger.user.manager.converter.dto.RegistrationConverter;
import com.myprod.mymessenger.user.manager.converter.dto.UserConverter;
import com.myprod.mymessenger.user.manager.configuration.SecurityConstants;
import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.model.request.Sign;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import com.myprod.mymessenger.user.manager.service.UserService;
import com.myprod.mymessenger.user.manager.util.cookie.AuthCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final UserService userService;

    private final RegistrationConverter registrationDataAssembler;

    private final UserConverter userAssembler;

    @Autowired
    public AuthController(
            UserService userService,
            RegistrationConverter registrationDataAssembler,
            UserConverter userAssembler
    ) {
        this.userService = userService;
        this.registrationDataAssembler = registrationDataAssembler;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/signup")
    public UserView signUp(@Valid @RequestBody Sign sign) {

        Sign signWithRoles = Sign.builder()
                .password(sign.getPassword())
                .phone(sign.getPhone())
                .roleList(Collections.singletonList(userService.findRole(Role.USER)))
                .build();

        com.myprod.mymessenger.user.manager.entity.User user = registrationDataAssembler.convertDto(signWithRoles);

        userService.createUser(user);

        return userAssembler.convertEntity(user);
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        AuthCookieUtil.clear(response, SecurityConstants.TOKEN_COOKIE);
    }
}
