package uz.onlineshop.dtoes.req;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.onlineshop.entity.Category;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  ProductRequest {
    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String color;
    private String imageUrl;
    private Long categoryId;
}
