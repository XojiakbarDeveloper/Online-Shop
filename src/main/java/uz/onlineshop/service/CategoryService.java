package uz.onlineshop.service;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.onlineshop.config.payload.base.ApiResult;
import uz.onlineshop.dtoes.req.CategoryRequest;
import uz.onlineshop.dtoes.res.CategoryResponse;
import uz.onlineshop.dtoes.res.ProductResponse;

import java.util.List;


public interface CategoryService {
    ApiResult<CategoryResponse> get(Long id);
    ApiResult<CategoryResponse> create(CategoryRequest request);
    ApiResult<Page<CategoryResponse>> getAll(Pageable pageable);
    ApiResult<CategoryResponse> update(Long id, CategoryRequest request);
    ApiResult<Void> delete(Long id);
    List<ProductResponse> getProductsByCategory(Long categoryId);
}
