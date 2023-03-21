package by.karpovich.shop.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoOut {

    private String message;

    private int rating;

    private String sender;

    private String date;
}
