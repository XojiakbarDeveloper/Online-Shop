package uz.onlineshop.orderservice.service;

import enums.orderEnums.OrderStatus;
import org.springframework.stereotype.Service;
import uz.onlineshop.orderservice.dtoes.req.OrderRequestDto;
import uz.onlineshop.orderservice.dtoes.res.OrderResponseDto;

import java.util.List;

@Service
public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    List<OrderResponseDto> getOrdersByUser(Long userId);

    OrderResponseDto getOrderById(Long orderId);

    List<OrderResponseDto> getAllOrders(OrderStatus status);

    OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status);

    void deleteOrder(Long orderId);
}
