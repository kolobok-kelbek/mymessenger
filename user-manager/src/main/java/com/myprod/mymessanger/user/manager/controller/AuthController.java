package com.myprod.mymessanger.user.manager.controller;

import com.myprod.mymessanger.user.manager.assembler.RegistrationDataAssembler;
import com.myprod.mymessanger.user.manager.assembler.UserAssembler;
import com.myprod.mymessanger.user.manager.dto.RegistrationDataDTO;
import com.myprod.mymessanger.user.manager.dto.UserDTO;
import com.myprod.mymessanger.user.manager.entity.Role;
import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationDataAssembler registrationDataAssembler;

    @Autowired
    private UserAssembler userAssembler;

    @PostMapping("/registration")
    public UserDTO registration(@RequestBody RegistrationDataDTO registrationDataDTO) {
        User user = registrationDataAssembler.readDTO(
                registrationDataDTO,
                Collections.singletonList(userService.findRole(Role.USER))
        );

        userService.saveUser(user);

        return userAssembler.writeDTO(user);
    }
}
