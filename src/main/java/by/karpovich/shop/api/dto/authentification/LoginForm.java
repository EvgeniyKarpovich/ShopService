package by.karpovich.shop.api.dto.authentification;

import lombok.Data;

@Data
public class LoginForm {

    private String username;

    private String password;
}