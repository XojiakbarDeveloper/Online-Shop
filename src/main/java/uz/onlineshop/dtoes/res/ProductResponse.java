package uz.onlineshop.dtoes.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.onlineshop.entity.Category;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String color;
    private Integer soldQuantity;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
}
