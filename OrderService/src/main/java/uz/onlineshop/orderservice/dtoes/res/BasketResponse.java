package uz.onlineshop.orderservice.dtoes.res;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class BasketResponse {
    private Long basketId;
    private Long userId;
    private List<BasketItemDto> items;
    private String status;
}
