package entity.basketEntity;

import entity.base.TimeLong;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "baskets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket extends TimeLong {


    @Column(nullable = false)
    private Long userId;

}
