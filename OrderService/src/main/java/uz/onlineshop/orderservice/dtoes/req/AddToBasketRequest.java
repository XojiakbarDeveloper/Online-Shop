package uz.onlineshop.orderservice.dtoes.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToBasketRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @Min(1)
    private Integer quantity;
}
