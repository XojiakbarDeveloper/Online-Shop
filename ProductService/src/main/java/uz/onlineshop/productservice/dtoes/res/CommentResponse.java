
package uz.onlineshop.productservice.dtoes.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String text;
    private Integer rating;
    private String username;
    private Long productId;
}
