package by.karpovich.shop.api.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationForSaveDto {

    private String name;

    @NotBlank
    private String message;

    private Long userId;
}
