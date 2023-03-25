package by.karpovich.shop.api.dto.authentification;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
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
