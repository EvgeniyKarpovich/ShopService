package by.karpovich.shop.api.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String name;

    private String recipient;

    private String from;

    private String message;

    private String date;
}
