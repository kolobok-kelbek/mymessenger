package com.myprod.mymessenger.user.manager.converter.user;

import com.myprod.mymessenger.user.manager.entity.Email;
import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserViewToUserConverter implements Converter<UserView, User> {
  @Override
  public User convert(MappingContext<UserView, User> context) {
    UserView userView = context.getSource();

    Set<Email> emails =
        userView
            .getEmails()
            .stream()
            .map(email -> Email.builder().email(email).build())
            .collect(Collectors.toSet());

    Set<PhoneNumber> phoneNumbers =
        userView
            .getPhoneNumbers()
            .stream()
            .map(phoneNumber -> PhoneNumber.builder().number(phoneNumber).build())
            .collect(Collectors.toSet());

    return User.builder()
        .id(userView.getId())
        .firstName(userView.getFirstName())
        .lastName(userView.getLastName())
        .surname(userView.getSurname())
        .username(userView.getUsername())
        .phone(userView.getMainPhone())
        .emails(emails)
        .phoneNumbers(phoneNumbers)
        .build();
  }
}
