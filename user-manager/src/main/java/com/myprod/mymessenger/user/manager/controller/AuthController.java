package com.myprod.mymessenger.user.manager.controller;

import com.myprod.mymessenger.user.manager.configuration.SecurityConstants;
import com.myprod.mymessenger.user.manager.converter.SimpleConverter;
import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.converter.factory.ConverterFactory;
import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.input.Sign;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import com.myprod.mymessenger.user.manager.service.UserService;
import com.myprod.mymessenger.user.manager.util.cookie.AuthCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final UserService userService;

    private final SimpleConverter<Sign, User> signToUserConverter;

    private final SimpleConverter<User, UserView> UserToUserViewConverter;

    @Autowired
    public AuthController(final UserService userService, final ConverterFactory converterFactory) {
        this.userService = userService;

        this.signToUserConverter = converterFactory.createSimpleConverter();
        this.UserToUserViewConverter = converterFactory.createSimpleConverter();
    }

    @PostMapping("/signup")
    public UserView signUp(@Valid @RequestBody final Sign sign) throws ConvertException {
        if (userService.userExistsByPhone(sign.getPhone())) {
            throw new ResponseStatusException(BAD_REQUEST, "User with this phone number exist.");
        }

        Sign signWithRoles = Sign.builder()
                .password(sign.getPassword())
                .phone(sign.getPhone())
                .roleList(Collections.singletonList(userService.findRole(Role.USER)))
                .build();

        User user = signToUserConverter.convert(signWithRoles, User.class);

        userService.createUser(user);

        return UserToUserViewConverter.convert(user, UserView.class);
    }

    @GetMapping("/logout")
    public void logout(final HttpServletResponse response) {
        AuthCookieUtil.clear(response, SecurityConstants.TOKEN_COOKIE);
    }
}
