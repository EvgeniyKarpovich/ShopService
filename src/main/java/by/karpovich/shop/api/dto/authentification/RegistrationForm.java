package by.karpovich.shop.api.dto.authentification;

import by.karpovich.shop.api.validation.emailValidator.ValidEmail;
import by.karpovich.shop.api.validation.usernameValidation.ValidUsername;
import by.karpovich.shop.jpa.entity.StatusUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationForm {

    @ValidUsername
    @NotBlank(message = "Enter name")
    private String username;

    @ValidEmail
    @NotBlank(message = "Enter email")
    private String email;

    private StatusUser status;

    @NotBlank(message = "Enter password")
    private String password;
}