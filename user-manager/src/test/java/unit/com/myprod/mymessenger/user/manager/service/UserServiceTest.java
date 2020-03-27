package unit.com.myprod.mymessenger.user.manager.service;

import com.github.javafaker.Faker;
import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.repository.EmailRepository;
import com.myprod.mymessenger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessenger.user.manager.repository.RoleRepository;
import com.myprod.mymessenger.user.manager.repository.UserRepository;
import com.myprod.mymessenger.user.manager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
final public class UserServiceTest {
    UserService userService;

    Faker faker;

    String phone;

    Date date;

    @Mock
    PhoneNumberRepository phoneRepo;

    @Mock
    UserRepository userRepo;

    @Mock
    RoleRepository roleRepo;

    @Mock
    EmailRepository emailRepo;

    @BeforeEach
    void setUp() {
        faker = Faker.instance();

        phone = faker.phoneNumber().phoneNumber();
        date = faker.date().birthday();
    }

    @Test
    void addPhoneNumber() {
        Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());

        userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

        PhoneNumber number = PhoneNumber.builder()
                .number(phone)
                .build();

        userService.addPhoneNumber(number);

        verify(phoneRepo, times(1)).save(any());
    }

    @Test
    void deletePhoneNumber() {
        Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());

        userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

        PhoneNumber number = PhoneNumber.builder()
                .number(phone)
                .build();
        userService.addPhoneNumber(number);
        userService.deletePhoneNumber(number);
        verify(phoneRepo, times(1)).delete(number);
    }

    @Test
    void findUser() {

        Optional<User> user = Optional.of(
                User.builder()
                        .phone(phone)
                        .password(faker.yoda().quote())
                        .build()
        );

        Mockito.when(userRepo.findById(any())).thenReturn(user);

        userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

        User foundUser = userService.findUser(UUID.randomUUID());

        assertEquals(phone, foundUser.getPhone());
    }
}
