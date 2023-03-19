package by.karpovich.shop.api.dto.authentification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private List<String> roles;
}
