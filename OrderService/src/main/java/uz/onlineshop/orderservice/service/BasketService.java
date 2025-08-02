package uz.onlineshop.orderservice.service;

import org.springframework.stereotype.Service;
import uz.onlineshop.orderservice.dtoes.req.AddToBasketRequest;
import uz.onlineshop.orderservice.dtoes.req.UpdateItemDTO;
import uz.onlineshop.orderservice.dtoes.res.BasketResponse;
import uz.onlineshop.orderservice.dtoes.res.ResponseMessage;

import java.util.List;

@Service
public interface BasketService {

    BasketResponse getUserBasket(String userId);

    BasketResponse addToBasket(AddToBasketRequest request);

    ResponseMessage removeItem(Long itemId);

    ResponseMessage clearBasket(Long userId);

    List<BasketResponse> getAllBasketsByStatus(String status);

    ResponseMessage updateBasketProductQuantity(UpdateItemDTO addToBasketRequest);

}
