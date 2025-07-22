package uz.onlineshop.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.onlineshop.productservice.entity.base.TimeLong;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends TimeLong {

    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Integer soldQuantity; // nechta sotilgani
    private String color;
    private Boolean isAvailable;
    private String imageUrl;
    @ManyToOne
    private Category category;



}
