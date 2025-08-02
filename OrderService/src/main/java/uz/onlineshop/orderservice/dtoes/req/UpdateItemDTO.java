package uz.onlineshop.orderservice.dtoes.req;

import lombok.Data;

@Data
public class UpdateItemDTO {

    private Long userId;

    private Long basketId;

    private Long productId;

    private Integer quantity;


}
