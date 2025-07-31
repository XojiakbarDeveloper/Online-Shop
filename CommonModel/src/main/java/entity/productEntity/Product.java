package entity.productEntity;

import entity.base.TimeLong;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends TimeLong {

    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer soldQuantity;
    private String color;
    private Boolean isAvailable;
    private String imageUrl;
    @ManyToOne
    private Category category;



}
