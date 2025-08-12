package uz.onlineshop.orderservice.controller;

import enums.orderEnums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.orderservice.dtoes.req.OrderRequestDto;
import uz.onlineshop.orderservice.dtoes.res.OrderResponseDto;
import uz.onlineshop.orderservice.dtoes.res.ResponseMessage;
import uz.onlineshop.orderservice.service.OrderService;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Yangi buyurtma yaratish",
            description = "Foydalanuvchi savatchasidagi mahsulotlar asosida yangi buyurtma yaratish")
    @PostMapping
    public ResponseEntity<ResponseMessage> createOrder(
            @Parameter(description = "Buyurtma yaratish so'rovi", required = true)
            @RequestBody OrderRequestDto request) throws AuthenticationException {
        ResponseMessage response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Foydalanuvchi buyurtmalarini olish",
            description = "Berilgan foydalanuvchi IDsi bo'yicha barcha buyurtmalar ro'yxatini olish")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(
            @Parameter(description = "Foydalanuvchi IDsi", required = true, example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @Operation(summary = "Buyurtma ma'lumotlarini olish",
            description = "Berilgan ID bo'yicha bitta buyurtmaning to'liq ma'lumotlarini olish")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @Parameter(description = "Buyurtma IDsi", required = true, example = "1")
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @Operation(summary = "Barcha buyurtmalarni filtrlash",
            description = "Status bo'yicha filtrlangan yoki barcha buyurtmalar ro'yxatini olish")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(
            @Parameter(description = "Buyurtma statusi (masalan: PENDING, COMPLETED)", required = false)
            @RequestParam(name = "status", required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getAllOrders(status));
    }

    @Operation(summary = "Buyurtma statusini yangilash",
            description = "Administrator tomonidan buyurtma statusini o'zgartirish")
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateStatus(
            @Parameter(description = "Buyurtma IDsi", required = true, example = "1")
            @PathVariable Long orderId,
            @Parameter(description = "Yangi status", required = true)
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    @Operation(summary = "Buyurtmani o'chirish",
            description = "Administrator tomonidan buyurtmani tizimdan olib tashlash")
    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "O'chiriladigan buyurtma IDsi", required = true, example = "1")
            @PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
