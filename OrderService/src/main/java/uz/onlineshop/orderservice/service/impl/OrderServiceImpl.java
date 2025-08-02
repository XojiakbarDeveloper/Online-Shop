package uz.onlineshop.orderservice.service.impl;

import entity.authEntity.User;
import entity.basketEntity.Basket;
import entity.orderEntity.Order;
import entity.orderEntity.OrderItem;
import entity.productEntity.Product;
import enums.basketEnums.BasketStatus;
import enums.orderEnums.OrderStatus;
import enums.orderEnums.PaymentMethod;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.authRepository.UserRepository;
import repository.basketRepository.BasketRepository;
import repository.orderRepository.OrderRepository;
import repository.productRepository.ProductRepository;
import uz.onlineshop.orderservice.dtoes.req.OrderRequestDto;
import uz.onlineshop.orderservice.dtoes.res.OrderResponseDto;
import uz.onlineshop.orderservice.mapper.BasketItemMapper;
import uz.onlineshop.orderservice.mapper.OrderMapper;
import uz.onlineshop.orderservice.service.OrderService;


import javax.security.sasl.AuthenticationException;
import java.math.BigDecimal;
import java.util.List;
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
    public OrderResponseDto createOrder(OrderRequestDto request) throws AuthenticationException {

        validateOrderRequest(request);

        if (request.getBasketId() != null) {
            return createOrderFromBasket(request);
        } else {
            throw new IllegalArgumentException("Direct order creation without basket is not supported in this implementation");
        }
    }

    private OrderResponseDto createOrderFromBasket(OrderRequestDto request) throws AuthenticationException {
        Basket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new EntityNotFoundException("Basket not found with id: " + request.getBasketId()));

        if (basket.getItems() == null || basket.getItems().isEmpty()) {
            throw new IllegalArgumentException("Basket is empty - cannot create order");
        }

        boolean hasValidItems = basket.getItems().stream()
                .anyMatch(item -> !item.getDeleted());

        if (!hasValidItems) {
            throw new IllegalArgumentException("Basket contains only deleted items - cannot create order");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));

        if (!basket.getUserId().equals(user.getId())) {
            throw new AuthenticationException("Basket does not belong to the specified user");
        }

        Order order = buildOrderFromRequest(request, user);

        List<OrderItem> orderItems = convertBasketItemsToOrderItems(basket, order);

        if (orderItems.isEmpty()) {
            throw new IllegalStateException("No valid items found in basket after conversion");
        }

        order.setItems(orderItems);

        calculateAndSetOrderTotal(order);

        Order savedOrder = orderRepository.save(order);

        updateBasketStatus(basket);

        clearBasketItems(basket);

        return OrderMapper.toDto(savedOrder);
    }

    private void validateOrderRequest(OrderRequestDto request) {
        if (request == null) {
            throw new IllegalArgumentException("Order request cannot be null");
        }
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (request.getBasketId() == null) {
            throw new IllegalArgumentException("Basket ID cannot be null");
        }
        if (request.getShippingAddress() == null || request.getShippingAddress().isBlank()) {
            throw new IllegalArgumentException("Shipping address cannot be empty");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
    }

    private Order buildOrderFromRequest(OrderRequestDto request, User user) {
        return Order.builder()
                .user(user)
                .shippingAddress(request.getShippingAddress())
                .phoneNumber(request.getPhoneNumber())
                .note(request.getNote())
                .paymentMethod(request.getPaymentMethod() != null ?
                        request.getPaymentMethod() : PaymentMethod.CASH)
                .status(OrderStatus.PENDING)
                .build();
    }

    private List<OrderItem> convertBasketItemsToOrderItems(Basket basket, Order order) {
        return basket.getItems().stream()
                .filter(item -> !item.getDeleted())
                .map(basketItem -> {
                    Product product = productRepository.findById(basketItem.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Product not found with id: " + basketItem.getProductId()));
                    return BasketItemMapper.toOrderItem(basketItem, product, order);
                })
                .collect(Collectors.toList());
    }

    private void calculateAndSetOrderTotal(Order order) {
        BigDecimal total = order.getItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(total);
    }

    private void updateBasketStatus(Basket basket) {
        basket.setStatus(BasketStatus.ORDERED);
        basketRepository.save(basket);
    }

    private void clearBasketItems(Basket basket) {
        basket.getItems().clear();
        basketRepository.save(basket);
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
