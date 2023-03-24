package by.karpovich.shop.api.dto.notification;

import by.karpovich.shop.api.validation.userIdValidator.ValidUserId;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDtoForSend {

    @NotBlank(message = "Enter name")
    private String name;

    @NotBlank(message = "Enter message")
    private String message;

    private List<@ValidUserId Long> usersId;
}
