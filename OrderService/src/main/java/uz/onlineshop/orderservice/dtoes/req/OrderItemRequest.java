package uz.onlineshop.orderservice.dtoes.req;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
