package repository.basketRepository;

import entity.basketEntity.Basket;
import enums.basketEnums.BasketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {


    Optional<Basket> findByUserIdAndStatus(Long userId, enums.basketEnums.BasketStatus status);

    List<Basket> findAllByUserId(Long userId);

    List<Basket> findAllByStatus(BasketStatus status);
}
