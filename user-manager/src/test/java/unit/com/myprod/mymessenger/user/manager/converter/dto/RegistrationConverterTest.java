package unit.com.myprod.mymessenger.user.manager.converter.dto;

import com.github.javafaker.Faker;
import com.myprod.mymessenger.user.manager.converter.dto.RegistrationConverter;
import com.myprod.mymessenger.user.manager.entity.Role;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.request.Sign;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RegistrationConverterTest {

    final Faker faker = Faker.instance();

    final RegistrationConverter registrationAssembler = new RegistrationConverter();

    final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void convertDTO() {
        Role role = Role.builder()
                .name(Role.USER)
                .build();

        Sign sign = Sign.builder()
                .phone(faker.phoneNumber().phoneNumber())
                .password(faker.lorem().word())
                .roleList(List.of(role))
                .build();

        User user = registrationAssembler.convertDto(sign);

        assertEquals(user.getPhone(), sign.getPhone());
        assertTrue(passwordEncoder.matches(sign.getPassword(), user.getPassword()));
        assertEquals(user.getRoles(), sign.getRoleList());
    }
}