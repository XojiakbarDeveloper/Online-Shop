package uz.onlineshop.mapper;

import uz.onlineshop.dtoes.req.CategoryRequest;
import uz.onlineshop.dtoes.res.CategoryResponse;
import uz.onlineshop.entity.Category;

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
