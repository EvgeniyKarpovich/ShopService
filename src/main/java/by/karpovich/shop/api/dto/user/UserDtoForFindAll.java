package by.karpovich.shop.api.dto.user;

import by.karpovich.shop.jpa.entity.StatusUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDtoForFindAll {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String username;

    private String email;

    private List<String> roles;

    private StatusUser status;
}
