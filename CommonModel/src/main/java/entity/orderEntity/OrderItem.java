package entity.orderEntity;

import entity.base.TimeLong;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@SQLDelete(sql = "UPDATE order_items SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class OrderItem extends TimeLong {


    private Long productId;

    private String productName;

    private BigDecimal price;

    private int quantity;

    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

}
