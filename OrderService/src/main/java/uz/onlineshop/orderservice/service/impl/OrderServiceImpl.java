package uz.onlineshop.orderservice.service.impl;

import entity.authEntity.User;
import entity.basketEntity.Basket;
import entity.basketEntity.BasketItem;
import entity.orderEntity.Order;
import entity.orderEntity.OrderItem;
import entity.productEntity.Product;
import enums.basketEnums.BasketStatus;
import enums.orderEnums.OrderStatus;

import enums.orderEnums.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.authRepository.UserRepository;
import repository.basketRepository.BasketRepository;
import repository.orderRepository.OrderRepository;
import repository.productRepository.ProductRepository;
import uz.onlineshop.orderservice.dtoes.req.OrderRequestDto;
import uz.onlineshop.orderservice.dtoes.res.OrderItemResponse;
import uz.onlineshop.orderservice.dtoes.res.OrderResponseDto;
import uz.onlineshop.orderservice.dtoes.res.ResponseMessage;
import uz.onlineshop.orderservice.mapper.BasketItemMapper;
import uz.onlineshop.orderservice.mapper.OrderMapper;
import uz.onlineshop.orderservice.service.OrderService;


import javax.security.sasl.AuthenticationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    @Override
    @Transactional
    public ResponseMessage createOrder(OrderRequestDto request) {
        try {

            Optional<User> userOpt = userRepository.findById(request.getUserId())
                    .filter(u -> Boolean.FALSE.equals(u.getDeleted()));
            if (userOpt.isEmpty()) {
                String msg = "User not found with id: " + request.getUserId();
                return new ResponseMessage(msg, msg, false);
            }
            User user = userOpt.get();


            Optional<Basket> basketOpt = basketRepository.findById(request.getBasketId())
                    .filter(b -> Boolean.FALSE.equals(b.getDeleted()));
            if (basketOpt.isEmpty()) {
                String msg = "Basket not found with id: " + request.getBasketId();
                return new ResponseMessage(msg, msg, false);
            }
            Basket basket = basketOpt.get();

            if (basket.getItems() == null || basket.getItems().isEmpty()) {
                String msg = "Basket is empty, cannot create order";
                return new ResponseMessage(msg, msg, false);
            }


            Order order = new Order();
            order.setOrderNumber(generateOrderNumber());
            order.setUser(user);
            order.setStatus(OrderStatus.PENDING);
            order.setPaymentStatus(PaymentStatus.UNPAID);
            order.setPaymentMethod(request.getPaymentMethod());
            order.setShippingAddress(request.getShippingAddress());
            order.setPhoneNumber(request.getPhoneNumber());
            order.setNote(request.getNote());

            BigDecimal totalPrice = BigDecimal.ZERO;


            for (BasketItem basketItem : basket.getItems()) {
                Optional<Product> productOpt = productRepository.findById(basketItem.getProductId())
                        .filter(p -> Boolean.FALSE.equals(p.getDeleted()));
                if (productOpt.isEmpty()) {
                    String msg = "Product not found with id: " + basketItem.getProductId();
                    return new ResponseMessage(msg, msg, false);
                }
                Product product = productOpt.get();

                BigDecimal itemPrice = product.getPrice();
                BigDecimal itemTotal = itemPrice.multiply(BigDecimal.valueOf(basketItem.getQuantity()));

                OrderItem orderItem = OrderItem.builder()
                        .productId(product.getId())
                        .productName(product.getProductName())
                        .price(itemPrice)
                        .quantity(basketItem.getQuantity())
                        .totalPrice(itemTotal)
                        .order(order)
                        .build();

                order.getItems().add(orderItem);
                totalPrice = totalPrice.add(itemTotal);
            }

            order.setTotalPrice(totalPrice);


            Order savedOrder = orderRepository.save(order);


            basket.setStatus(BasketStatus.ORDERED);
            basketRepository.save(basket);


            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .id(savedOrder.getId())
                    .orderNumber(savedOrder.getOrderNumber())
                    .status(savedOrder.getStatus())
                    .totalPrice(savedOrder.getTotalPrice())
                    .createdAt(savedOrder.getCreatedAt())
                    .items(savedOrder.getItems().stream()
                            .map(item -> new OrderItemResponse(
                                    item.getProductId(),
                                    item.getProductName(),
                                    item.getQuantity(),
                                    item.getPrice(),
                                    item.getTotalPrice()
                            ))
                            .toList())
                    .build();


            return new ResponseMessage(
                    "Order created successfully",
                    orderResponseDto,
                    true
            );

        } catch (Exception e) {
            return new ResponseMessage(
                    "Error creating order",
                    e.getMessage(),
                    false
            );
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }



    @Override
    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(OrderStatus status) {
        List<Order> orders = (status != null)
                ? orderRepository.findByStatus(status)
                : orderRepository.findAll();

        return orders.stream().map(OrderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        Order updated = orderRepository.save(order);
        return OrderMapper.toDto(updated);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}
