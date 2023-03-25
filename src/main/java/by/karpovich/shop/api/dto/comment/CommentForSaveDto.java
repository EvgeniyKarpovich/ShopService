package by.karpovich.shop.api.dto.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentForSaveDto {

    @NotBlank
    private String message;

    @Min(value = 1, message = "min 1")
    @Max(value = 5, message = "max 5")
    private int rating;

    private Long productId;
}
