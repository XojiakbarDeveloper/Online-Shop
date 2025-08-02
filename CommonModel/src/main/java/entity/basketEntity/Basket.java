package entity.basketEntity;

import entity.base.TimeLong;
import entity.basketEntity.BasketItem;
import enums.basketEnums.BasketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "baskets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Basket extends TimeLong {

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> items;

    @Enumerated(EnumType.STRING)
    private BasketStatus status = BasketStatus.ACTIVE; // ACTIVE, ORDERED, etc.
}
