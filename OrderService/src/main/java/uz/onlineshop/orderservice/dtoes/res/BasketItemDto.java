package uz.onlineshop.orderservice.dtoes.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String productName;
    private Double productPrice;
}
