package uz.onlineshop.productservice.service;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.ProductRequest;
import uz.onlineshop.productservice.dtoes.res.ProductResponse;

@Service
public interface ProductService {

    ApiResult<ProductResponse> create(ProductRequest request);

    ApiResult<ProductResponse> update(Long id, ProductRequest request);

    ApiResult<?> delete(Long id);

    ApiResult<ProductResponse> get(Long id);

    ApiResult<Page<ProductResponse>> getAll(Pageable pageable);




}
