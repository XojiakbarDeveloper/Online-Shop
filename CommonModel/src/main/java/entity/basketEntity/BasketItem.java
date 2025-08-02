package entity.basketEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.base.TimeLong;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity(name = "basket_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasketItem extends TimeLong {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    @JsonIgnore
    private Basket basket;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    // Optional: productName, price, discount, etc. — snapshot sifatida
    private String productName;
    private Double productPrice;
}
