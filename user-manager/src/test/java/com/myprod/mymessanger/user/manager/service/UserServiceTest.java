package com.myprod.mymessanger.user.manager.service;

import com.myprod.mymessanger.user.manager.entity.PhoneNumber;
import com.myprod.mymessanger.user.manager.repository.PhoneNumberRepository;
import com.myprod.mymessanger.user.manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  UserService userService;
  final String phone = "+99999999999";
  final Date date = new Date(2020, Calendar.APRIL, 3);

  @Mock
  PhoneNumberRepository phoneRepo;

  @Mock
  UserRepository userRepo;


  @Test
  void addPhoneNumber() {
    Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());
    userService = new UserService(userRepo, phoneRepo);
    PhoneNumber number = PhoneNumber.builder()
      .uuid(UUID.randomUUID())
      .number(phone)
      .createAt(date)
      .build();
    userService.addPhoneNumber(number);
    verify(phoneRepo, times(1)).save(any());

  }

  @Test
  void deletePhoneNumber() {
    Mockito.when(phoneRepo.save(any())).then(returnsFirstArg());
    userService = new UserService(userRepo, phoneRepo);
    PhoneNumber number = PhoneNumber.builder()
      .uuid(UUID.randomUUID())
      .number(phone)
      .createAt(date)
      .build();
    userService.addPhoneNumber(number);
    userService.deletePhoneNumber(number);
    verify(phoneRepo, times(1)).delete(number);
  }


}
