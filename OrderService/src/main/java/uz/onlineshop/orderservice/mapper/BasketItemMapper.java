package uz.onlineshop.orderservice.mapper;

import entity.basketEntity.BasketItem;
import entity.orderEntity.Order;
import entity.orderEntity.OrderItem;
import entity.productEntity.Product;

import java.math.BigDecimal;

public class BasketItemMapper {

    public static OrderItem toOrderItem(BasketItem basketItem, Product product, Order order) {
        if (basketItem == null || product == null || order == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        return OrderItem.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(basketItem.getQuantity())
                .totalPrice(calculateItemTotal(product.getPrice(), basketItem.getQuantity()))
                .order(order)
                .build();
    }

    private static BigDecimal calculateItemTotal(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
