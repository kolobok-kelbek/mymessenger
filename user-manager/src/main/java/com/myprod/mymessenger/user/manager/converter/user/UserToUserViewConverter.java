package com.myprod.mymessenger.user.manager.converter.user;

import com.myprod.mymessenger.user.manager.entity.Email;
import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserToUserViewConverter implements Converter<User, UserView> {
    @Override
    public UserView convert(MappingContext<User, UserView> context) {
        User user = context.getSource();

        UserView.UserViewBuilder builder = UserView.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .mainPhone(user.getPhone());

        if (null != user.getEmails() && !user.getEmails().isEmpty()) {
            Set<String> emails = user.getEmails()
                    .stream()
                    .map(Email::getEmail)
                    .collect(Collectors.toSet());

            builder.emails(emails);
        }

        if (null != user.getPhoneNumbers() && !user.getPhoneNumbers().isEmpty()) {
            Set<String> phoneNumbers = user.getPhoneNumbers()
                    .stream()
                    .map(PhoneNumber::getNumber)
                    .collect(Collectors.toSet());

            builder.phoneNumbers(phoneNumbers);
        }

        return builder.build();
    }
}
