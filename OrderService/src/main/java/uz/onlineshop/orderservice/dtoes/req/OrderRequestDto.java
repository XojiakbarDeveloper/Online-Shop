package uz.onlineshop.orderservice.dtoes.req;

import entity.basketEntity.Basket;
import enums.orderEnums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequestDto {
    private Long userId;
    private Long basketId;
    private String shippingAddress;
    private String phoneNumber;
    private String note;
    private PaymentMethod paymentMethod;
}
