package by.karpovich.shop.api.dto.user;

import by.karpovich.shop.api.dto.validation.emailValidator.ValidEmail;
import by.karpovich.shop.api.dto.validation.usernameValidation.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForUpdate {

    @ValidUsername
    @NotBlank(message = "Enter name")
    private String username;

    @ValidEmail
    @NotBlank(message = "Enter email")
    private String email;
}
