package uz.onlineshop.productservice.dtoes.res;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private String text;
    private Long productId;
    private Long userId;

    // Qo‘shimcha ma’lumotlar (FeignClient orqali to‘ldirsa bo‘ladi)
    private String username;
    private String productName;

    private LocalDateTime createdAt;
}
