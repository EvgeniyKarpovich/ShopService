package by.karpovich.shop.api.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentForSaveDto {

    private String message;

    private int rating;

    private Long productId;

    private Long userId;
}
