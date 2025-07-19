package uz.onlineshop.mapper;

import uz.onlineshop.dtoes.req.ProductRequest;
import uz.onlineshop.dtoes.res.ProductResponse;
import uz.onlineshop.entity.Category;
import uz.onlineshop.entity.Product;


public interface ProductMapper {


  public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .color(product.getColor())
                .imageUrl(product.getImageUrl())
                .stockQuantity(product.getStockQuantity())
                .soldQuantity(product.getSoldQuantity())
                .categoryName(product.getCategory().getName())
                .categoryId(product.getCategory().getId())
                .build();
  }



    static Product toEntity(ProductRequest request) {
        return Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .color(request.getColor())
                .imageUrl(request.getImageUrl())
                .stockQuantity(request.getStockQuantity())
                .build();
    }


}
