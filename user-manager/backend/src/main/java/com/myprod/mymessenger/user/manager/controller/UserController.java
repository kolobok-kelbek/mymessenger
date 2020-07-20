package com.myprod.mymessenger.user.manager.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.myprod.mymessenger.user.manager.converter.Converter;
import com.myprod.mymessenger.user.manager.converter.SimpleConverter;
import com.myprod.mymessenger.user.manager.converter.exception.ConvertException;
import com.myprod.mymessenger.user.manager.converter.factory.ConverterFactory;
import com.myprod.mymessenger.user.manager.entity.Email;
import com.myprod.mymessenger.user.manager.entity.PhoneNumber;
import com.myprod.mymessenger.user.manager.entity.User;
import com.myprod.mymessenger.user.manager.model.input.EmailData;
import com.myprod.mymessenger.user.manager.model.input.PaginationQuery;
import com.myprod.mymessenger.user.manager.model.input.PhoneNumberData;
import com.myprod.mymessenger.user.manager.model.view.ListView;
import com.myprod.mymessenger.user.manager.model.view.UserView;
import com.myprod.mymessenger.user.manager.service.UserService;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
  private final UserService userService;

  private final SimpleConverter<User, UserView> userToUserViewConverter;

  private final SimpleConverter<UserView, User> userViewToUserConverter;

  private final Converter<Page<User>, ListView<UserView>, UserView> listViewConverter;

  @Autowired
  public UserController(final UserService userService, final ConverterFactory converterFactory) {
    this.userService = userService;

    userToUserViewConverter = converterFactory.createSimpleConverter();
    userViewToUserConverter = converterFactory.createSimpleConverter();
    listViewConverter = converterFactory.createPageToListViewConverter();
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/user")
  public UserView getCurrentUser() throws ConvertException {
    return userToUserViewConverter.convert(userService.getCurrentUser(), UserView.class);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/user")
  public UserView updateCurrentUser(@RequestBody final UserView userView) throws ConvertException {
    if (!userService.getCurrentUser().getId().equals(userView.getId())) {
      throw new ResponseStatusException(FORBIDDEN);
    }

    User user = userViewToUserConverter.convert(userView, User.class);

    userService.updateUser(user);

    return userToUserViewConverter.convert(user, UserView.class);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/users/{uuid}")
  public UserView getUser(@PathVariable final UUID uuid) throws ConvertException {
    return userToUserViewConverter.convert(userService.findUser(uuid), UserView.class);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/users")
  public UserView createUser(@RequestBody final User user) throws ConvertException {
    userService.createUser(user);

    return userToUserViewConverter.convert(user, UserView.class);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/users")
  public UserView updateUser(@RequestBody final UserView userView) throws ConvertException {
    User user = userViewToUserConverter.convert(userView, User.class);

    userService.updateUser(user);

    return userToUserViewConverter.convert(user, UserView.class);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/users")
  public ListView<UserView> getLimitUsers(@Valid PaginationQuery paginationQuery)
      throws ConvertException {
    Page<User> page = userService.findLimitUsers(paginationQuery);

    return listViewConverter.convert(page, UserView.class);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/user/phone")
  public Set<String> createPhone(@Valid @RequestBody final PhoneNumberData phoneNumberData) {
    User user = userService.getCurrentUser();

    PhoneNumber phoneNumber =
        PhoneNumber.builder().user(user).number(phoneNumberData.getPhoneNumber()).build();

    userService.addPhoneNumber(phoneNumber);

    return userService.getPhoneNumbersCurrentUser();
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/user/phone")
  public Set<String> deletePhone(@Valid @RequestBody final PhoneNumberData phoneNumberData) {
    User user = userService.getCurrentUser();

    PhoneNumber delPhoneNumber =
        PhoneNumber.builder().user(user).number(phoneNumberData.getPhoneNumber()).build();
    PhoneNumber phoneNumber = userService.addPhoneNumber(delPhoneNumber);

    userService.deletePhoneNumber(phoneNumber);

    return userService.getPhoneNumbersCurrentUser();
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/user/email")
  public Set<String> createEmail(@Valid @RequestBody final EmailData email) {
    User user = userService.getCurrentUser();

    Email addEmail = Email.builder().user(user).email(email.getEmail()).build();

    userService.addEmail(addEmail);

    return userService.getEmailsCurrentUser();
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/user/email")
  public Set<String> deleteEmail(@Valid @RequestBody final EmailData email) {
    User user = userService.getCurrentUser();

    Email delEmail = Email.builder().user(user).email(email.getEmail()).build();

    userService.deleteEmail(delEmail);

    return userService.getEmailsCurrentUser();
  }
}
