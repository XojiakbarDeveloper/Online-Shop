package uz.onlineshop.orderservice.service.impl;

import entity.basketEntity.Basket;
import entity.basketEntity.BasketItem;
import enums.basketEnums.BasketStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.authRepository.UserRepository;
import repository.basketRepository.BasketItemRepository;
import repository.basketRepository.BasketRepository;
import uz.onlineshop.orderservice.dtoes.req.AddToBasketRequest;
import uz.onlineshop.orderservice.dtoes.req.UpdateItemDTO;
import uz.onlineshop.orderservice.dtoes.res.BasketResponse;
import uz.onlineshop.orderservice.dtoes.res.ResponseMessage;
import uz.onlineshop.orderservice.mapper.BasketMapper;
import uz.onlineshop.orderservice.service.BasketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final BasketItemRepository basketItemRepository;

    @Override
    public BasketResponse getUserBasket(String userId) {
        Optional<Basket> optionalBasket = basketRepository
                .findByUserIdAndStatus(Long.valueOf(userId), BasketStatus.ACTIVE)
                .filter(b -> !b.getDeleted());

        if (optionalBasket.isEmpty()) {
            return BasketResponse.builder()
                    .basketId(null)
                    .userId(Long.valueOf(userId))
                    .status("NOT_FOUND")
                    .items(List.of())
                    .build();
        }

        Basket basket = optionalBasket.get();
        List<BasketItem> activeItems = basket.getItems()
                .stream()
                .filter(item -> !item.getDeleted())
                .toList();

        basket.setItems(activeItems);
        return BasketMapper.toResponse(basket);
    }

    @Override
    @Transactional
    public BasketResponse addToBasket(AddToBasketRequest request) {
        Basket basket = basketRepository
                .findByUserIdAndStatus(request.getUserId(), BasketStatus.ACTIVE)
                .filter(b -> !b.getDeleted())
                .orElseGet(() -> Basket.builder()
                        .userId(request.getUserId())
                        .status(BasketStatus.ACTIVE)
                        .deleted(false)
                        .items(new ArrayList<>())
                        .build());

        Optional<BasketItem> existingItemOpt = basket.getItems()
                .stream()
                .filter(item -> !item.getDeleted() && item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            BasketItem item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            BasketItem item = BasketItem.builder()
                    .basket(basket)
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .deleted(false)
                    .build();
            basket.getItems().add(item);
        }

        basketRepository.save(basket);
        return BasketMapper.toResponse(basket);
    }

    @Override
    @Transactional
    public ResponseMessage removeItem(Long itemId) {
        Optional<BasketItem> optionalItem = basketItemRepository.findById(itemId)
                .filter(i -> !i.getDeleted());

        if (optionalItem.isEmpty()) {
            return new ResponseMessage("Mahsulot topilmadi", null, false);
        }

        BasketItem item = optionalItem.get();
        item.setDeleted(true);
        basketItemRepository.save(item);

        return new ResponseMessage("Mahsulot savatchadan muvaffaqiyatli olib tashlandi", null, true);
    }

    @Override
    @Transactional
    public ResponseMessage clearBasket(Long userId) {
        Optional<Basket> optionalBasket = basketRepository
                .findByUserIdAndStatus(userId, BasketStatus.ACTIVE)
                .filter(b -> !b.getDeleted());

        if (optionalBasket.isEmpty()) {
            return new ResponseMessage("Foydalanuvchi savatchasi topilmadi", null, false);
        }

        Basket basket = optionalBasket.get();
        basket.getItems()
                .stream()
                .filter(item -> !item.getDeleted())
                .forEach(item -> item.setDeleted(true));

        basketRepository.save(basket);
        return new ResponseMessage("Savatcha tozalandi", null, true);
    }

    @Override
    public List<BasketResponse> getAllBasketsByStatus(String status) {
        List<Basket> baskets;

        if (status != null) {
            try {
                baskets = basketRepository.findAllByStatus(BasketStatus.valueOf(status));
            } catch (IllegalArgumentException e){
                throw new RuntimeException(e.getMessage());
            }

        } else {
            baskets = basketRepository.findAll();
        }

        return baskets.stream()
                .filter(b -> !b.getDeleted())
                .map(BasketMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ResponseMessage updateBasketProductQuantity(UpdateItemDTO dto) {

        Optional<Basket> optionalBasket = basketRepository.findByUserIdAndStatus(dto.getUserId(), BasketStatus.ACTIVE);

        if (optionalBasket.isEmpty()) {
            return new ResponseMessage("Basket not found for the user", null, false);
        }

        Basket basket = optionalBasket.get();

        Optional<BasketItem> optionalItem = basket.getItems().stream()
                .filter(item -> item.getProductId().equals(dto.getProductId()))
                .findFirst();

        if (optionalItem.isEmpty()) {
            return new ResponseMessage("Product not found in basket", null, false);
        }

        BasketItem item = optionalItem.get();

        if (dto.getQuantity() <= 0) {

            basket.getItems().remove(item);
            basketItemRepository.delete(item);
        } else {
            item.setQuantity(dto.getQuantity());
            basketItemRepository.save(item);
        }

        basketRepository.save(basket);

        return new ResponseMessage("Basket updated successfully!", basket, true);
    }


}
