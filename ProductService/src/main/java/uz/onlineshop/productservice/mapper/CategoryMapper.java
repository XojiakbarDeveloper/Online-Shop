package uz.onlineshop.productservice.mapper;


import uz.onlineshop.productservice.dtoes.req.CategoryRequest;
import uz.onlineshop.productservice.dtoes.res.CategoryResponse;
import uz.onlineshop.productservice.entity.Category;

public interface CategoryMapper {


    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }


    public static Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }
}
