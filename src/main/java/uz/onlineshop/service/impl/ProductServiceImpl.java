package uz.onlineshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.onlineshop.config.payload.base.ApiResult;
import uz.onlineshop.dtoes.req.ProductRequest;
import uz.onlineshop.dtoes.res.ProductResponse;
import uz.onlineshop.entity.Category;
import uz.onlineshop.entity.Product;
import uz.onlineshop.mapper.ProductMapper;
import uz.onlineshop.repository.CategoryRepository;
import uz.onlineshop.repository.ProductRepository;
import uz.onlineshop.service.ProductService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ApiResult<ProductResponse> create(ProductRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return ApiResult.error("Category not found");
        }

        Product product = ProductMapper.toEntity(request);

        product.setCategory(optionalCategory.get());
        Product savedProduct = productRepository.save(product);

        return ApiResult.successResponse(ProductMapper.toProductResponse(savedProduct));
    }

    @Override
    public ApiResult<ProductResponse> update(Long id, ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ApiResult.error("Product not found with id: " + id);
        }

        Product product = optionalProduct.get();
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setColor(request.getColor());
        product.setImageUrl(request.getImageUrl());
        product.setStockQuantity(request.getStockQuantity());

        Product updatedProduct = productRepository.save(product);
        return ApiResult.successResponse(ProductMapper.toProductResponse(updatedProduct));
    }

    @Override
    public ApiResult<?> delete(Long id) {
        if (!productRepository.existsById(id)) {
            return ApiResult.errorResponse("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return ApiResult.successResponse("Product successfully deleted");
    }

    @Override
    public ApiResult<ProductResponse> get(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return ApiResult.error("Product not found with id: " + id);
        }
        return ApiResult.successResponse(ProductMapper.toProductResponse(optionalProduct.get()));
    }

    @Override
    public ApiResult<Page<ProductResponse>> getAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Page<ProductResponse> responsePage = productPage.map(ProductMapper::toProductResponse);
        return ApiResult.successResponse(responsePage);
    }

}
