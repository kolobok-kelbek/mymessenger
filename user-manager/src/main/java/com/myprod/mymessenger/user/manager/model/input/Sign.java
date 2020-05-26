package com.myprod.mymessenger.user.manager.model.input;

import com.myprod.mymessenger.user.manager.entity.Role;
import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Sign {

  @NotEmpty(message = "Please provide a phone number.")
  @Pattern(regexp = "^[0-9-+]{9,15}$", message = "Please provide a correct phone number.")
  private String phone;

  @NotEmpty(message = "Please provide a password.")
  @Size(
      min = 8,
      max = 48,
      message = "Please enter a password of at least 8 and at most 48 characters.")
  private String password;

  private Collection<Role> roleList;
}
