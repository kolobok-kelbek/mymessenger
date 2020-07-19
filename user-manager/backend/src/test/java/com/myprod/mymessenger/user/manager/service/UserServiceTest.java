package com.myprod.mymessenger.user.manager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import com.myprod.mymessenger.user.manager.entity.Email;
import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.repository.EmailRepository;
import com.myprod.mymessenger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessenger.user.manager.repository.RoleRepository;
import com.myprod.mymessenger.user.manager.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public final class UserServiceTest {
  UserService userService;

  Faker faker;

  String phone;

  String mail;

  Date date;

  @Mock PhoneNumberRepository phoneRepo;

  @Mock UserRepository userRepo;

  @Mock RoleRepository roleRepo;

  @Mock EmailRepository emailRepo;

  private static final int ONE_INVOCATION = 1;

  private static final String FAILED_TO_SAVE_MESSAGE = "failed to save";

  private static final String FAILED_TO_FIND_MESSAGE = "failed to find";

  private static final String FAILED_TO_DELETE_MESSAGE = "failed to delete";

  @BeforeEach
  void setUp() {
    faker = Faker.instance();

    phone = faker.phoneNumber().phoneNumber();
    date = faker.date().birthday();
    mail = faker.internet().emailAddress();
  }

  @Test
  void addPhoneNumber() {
    Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    PhoneNumber number = PhoneNumber.builder().number(phone).build();

    userService.addPhoneNumber(number);

    verify(phoneRepo, times(ONE_INVOCATION)).save(any());
  }

  @Test
  void deletePhoneNumber() {
    Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    PhoneNumber number = PhoneNumber.builder().number(phone).build();
    userService.addPhoneNumber(number);
    userService.deletePhoneNumber(number);
    verify(phoneRepo, times(ONE_INVOCATION)).delete(number);
  }

  @Test
  void findUser() {

    Optional<User> user =
        Optional.of(User.builder().phone(phone).password(faker.yoda().quote()).build());

    Mockito.when(userRepo.findById(any())).thenReturn(user);

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    User foundUser = userService.findUser(UUID.randomUUID());

    assertEquals(phone, foundUser.getPhone());
  }

  @Test
  void addEmail() {
    Mockito.when(emailRepo.save(any())).then(returnsFirstArg());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    Email email = Email.builder().email(mail).build();
    userService.addEmail(email);
    verify(emailRepo, times(ONE_INVOCATION)).save(any());
  }

  @Test
  void deleteEmail() {
    Mockito.when(emailRepo.save(any())).then(returnsFirstArg());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);
    Email email = Email.builder().email(mail).build();
    userService.addEmail(email);
    userService.deleteEmail(email);
    verify(emailRepo, times(ONE_INVOCATION)).delete(email);
  }

  @Test
  void addPhoneNumberNegativeTest() {

    Mockito.when(phoneRepo.save(any()))
        .thenThrow(new IllegalArgumentException(FAILED_TO_SAVE_MESSAGE));

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    PhoneNumber number = PhoneNumber.builder().number(phone).build();

    Throwable thrown =
        assertThrows(IllegalArgumentException.class, () -> userService.addPhoneNumber(number));

    assertNotNull(thrown.getMessage());
    assertEquals(FAILED_TO_SAVE_MESSAGE, thrown.getMessage());
  }

  @Test
  void deletePhoneNumberNegativeTest() {
    PhoneNumber number = PhoneNumber.builder().number(phone).build();
    Mockito.doThrow(new IllegalArgumentException(FAILED_TO_DELETE_MESSAGE))
        .when(phoneRepo)
        .delete(any());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    Throwable thrown =
        assertThrows(IllegalArgumentException.class, () -> userService.deletePhoneNumber(number));

    assertNotNull(thrown.getMessage());
    assertEquals(FAILED_TO_DELETE_MESSAGE, thrown.getMessage());
  }

  @Test
  void findUserNegativeTest() {
    Mockito.when(userRepo.findById(any()))
        .thenThrow(new IllegalArgumentException(FAILED_TO_FIND_MESSAGE));

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);
    Throwable thrown =
        assertThrows(IllegalArgumentException.class, () -> userService.findUser(UUID.randomUUID()));

    assertNotNull(thrown.getMessage());
    assertEquals(FAILED_TO_FIND_MESSAGE, thrown.getMessage());
  }

  @Test
  void addEmailNegativeTest() {
    Mockito.when(emailRepo.save(any()))
        .thenThrow(new IllegalArgumentException(FAILED_TO_SAVE_MESSAGE));

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    Email email = Email.builder().email(mail).build();
    Throwable thrown =
        assertThrows(IllegalArgumentException.class, () -> userService.addEmail(email));

    assertNotNull(thrown.getMessage());
    assertEquals(FAILED_TO_SAVE_MESSAGE, thrown.getMessage());
  }

  @Test
  void deleteEmailNegativeTest() {
    Email email = Email.builder().email(mail).build();
    Mockito.doThrow(new IllegalArgumentException(FAILED_TO_DELETE_MESSAGE))
        .when(emailRepo)
        .delete(any());

    userService = new UserService(userRepo, phoneRepo, roleRepo, emailRepo);

    Throwable thrown =
        assertThrows(IllegalArgumentException.class, () -> userService.deleteEmail(email));

    assertNotNull(thrown.getMessage());
    assertEquals(FAILED_TO_DELETE_MESSAGE, thrown.getMessage());
  }
}
