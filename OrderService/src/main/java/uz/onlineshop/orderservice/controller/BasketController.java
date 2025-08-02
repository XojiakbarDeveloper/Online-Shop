package uz.onlineshop.orderservice.controller;

import enums.basketEnums.BasketStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.orderservice.dtoes.req.AddToBasketRequest;
import uz.onlineshop.orderservice.dtoes.req.UpdateItemDTO;
import uz.onlineshop.orderservice.dtoes.res.BasketResponse;
import uz.onlineshop.orderservice.dtoes.res.ResponseMessage;
import uz.onlineshop.orderservice.service.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @Operation(summary = "Savatchalarni status bo'yicha filtrlash")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<BasketResponse> getBasketsByStatus(
            @Parameter(description = "Savatcha statusi (masalan: ACTIVE, ORDERED)")
            @RequestParam(name = "status", required = false) String status) {
        return basketService.getAllBasketsByStatus(status);
    }

    @Operation(summary = "Foydalanuvchi savatchasini olish",
            description = "Berilgan foydalanuvchi IDsi bo'yicha savatchani olish")
    @GetMapping("/customer-basket")
    public ResponseEntity<BasketResponse> getUserBasket(
            @Parameter(description = "Foydalanuvchi IDsi", required = true, example = "123")
            @RequestParam(name = "userId") String userId) {
        return ResponseEntity.ok(basketService.getUserBasket(userId));
    }

    @Operation(summary = "Savatchaga mahsulot qo'shish",
            description = "Savatchaga yangi mahsulot qo'shish")
    @PostMapping("/add")
    public ResponseEntity<BasketResponse> addToBasket(
            @Parameter(description = "Mahsulot qo'shish so'rovi")
            @RequestBody AddToBasketRequest request) {
        return ResponseEntity.ok(basketService.addToBasket(request));
    }

    @Operation(summary = "Savatchadan mahsulotni o'chirish",
            description = "Berilgan IDga ega mahsulotni savatchadan olib tashlash")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ResponseMessage> removeItem(
            @Parameter(description = "O'chiriladigan mahsulot IDsi", required = true, example = "1")
            @PathVariable Long itemId) {
        return ResponseEntity.ok(basketService.removeItem(itemId));
    }

    @Operation(summary = "Savatchani tozalash",
            description = "Berilgan foydalanuvchi IDsi bo'yicha savatchadagi barcha mahsulotlarni o'chirish")
    @DeleteMapping("/clear")
    public ResponseEntity<ResponseMessage> clearBasket(
            @Parameter(description = "Foydalanuvchi IDsi", required = true, example = "123")
            @RequestParam Long userId) {
        return ResponseEntity.ok(basketService.clearBasket(userId));
    }

    @Operation(summary = "Savatchadagi mahsulot miqdorini yangilash",
            description = "Savatchadagi mavjud mahsulot miqdorini o'zgartirish")
    @PutMapping("/update-product-quantity")
    public ResponseEntity<ResponseMessage> updateBasketItem(
            @Parameter(description = "Mahsulot miqdori yangilash so'rovi")
            @RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(basketService.updateBasketProductQuantity(updateItemDTO));
    }

}
