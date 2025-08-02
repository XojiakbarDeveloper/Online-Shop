package uz.onlineshop.orderservice.mapper;

import entity.basketEntity.Basket;
import entity.basketEntity.BasketItem;
import entity.orderEntity.Order;
import entity.orderEntity.OrderItem;
import entity.productEntity.Product;
import uz.onlineshop.orderservice.dtoes.res.BasketItemDto;
import uz.onlineshop.orderservice.dtoes.res.BasketResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BasketMapper {

    public static BasketResponse toResponse(Basket basket) {
        List<BasketItemDto> itemDtoList = basket.getItems()
                .stream()
                .filter(item -> !item.getDeleted())
                .map(BasketMapper::toItemDto)
                .collect(Collectors.toList());

        return BasketResponse.builder()
                .basketId(basket.getId())
                .userId(basket.getUserId())
                .status(basket.getStatus().name())
                .items(itemDtoList)
                .build();
    }

    public static BasketItemDto toItemDto(BasketItem item) {
        return BasketItemDto.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .productName(item.getProductName())
                .productPrice(item.getProductPrice())
                .build();
    }


}
