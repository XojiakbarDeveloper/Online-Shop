package repository.basketRepository;

import entity.basketEntity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    List<BasketItem> findAllByBasketIdAndDeletedFalse(Long basketId);
}
