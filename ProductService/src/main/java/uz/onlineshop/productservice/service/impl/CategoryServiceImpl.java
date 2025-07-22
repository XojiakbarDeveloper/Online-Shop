package uz.onlineshop.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.CategoryRequest;
import uz.onlineshop.productservice.dtoes.res.CategoryResponse;
import uz.onlineshop.productservice.dtoes.res.ProductResponse;
import uz.onlineshop.productservice.entity.Category;
import uz.onlineshop.productservice.entity.Product;
import uz.onlineshop.productservice.mapper.CategoryMapper;
import uz.onlineshop.productservice.mapper.ProductMapper;
import uz.onlineshop.productservice.repository.CategoryRepository;
import uz.onlineshop.productservice.repository.ProductRepository;
import uz.onlineshop.productservice.service.CategoryService;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public  class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Override
    public ApiResult<CategoryResponse> get(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ApiResult.error("Category not found");
        }
        return ApiResult.successResponse(CategoryMapper.toCategoryResponse(optionalCategory.get()));
    }

    @Override
    public ApiResult<CategoryResponse> create(CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);
        categoryRepository.save(category);
        return ApiResult.successResponse(CategoryMapper.toCategoryResponse(category));
    }

    @Override
    public ApiResult<Page<CategoryResponse>> getAll(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        Page<CategoryResponse> responsePage = categoryPage.map(CategoryMapper::toCategoryResponse);
        return ApiResult.successResponse(responsePage);
    }

    @Override
    public ApiResult<CategoryResponse> update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ApiResult.error("Category not found");
        }
        Category category = optionalCategory.get();
        category.setName(request.getName());

        Category updatedCategory = categoryRepository.save(category);
        return ApiResult.successResponse(CategoryMapper.toCategoryResponse(updatedCategory));
    }

    @Override
    public ApiResult<Void> delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            return ApiResult.error("Category not found");
        }
        categoryRepository.deleteById(id);
        return ApiResult.successResponse();
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = productRepository.findAllByCategory(category);

        return products.stream()
                .map(ProductMapper::toProductResponse)
                .toList();
    }
}
