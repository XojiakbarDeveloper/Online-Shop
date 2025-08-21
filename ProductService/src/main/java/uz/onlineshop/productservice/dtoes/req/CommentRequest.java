package uz.onlineshop.productservice.dtoes.req;

import lombok.Data;

@Data
public class CommentRequest {
    private Long productId;
    private Long userId;
    private String text;
}
