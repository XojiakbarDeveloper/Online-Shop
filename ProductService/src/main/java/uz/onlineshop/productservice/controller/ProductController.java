package uz.onlineshop.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.ProductRequest;
import uz.onlineshop.productservice.dtoes.res.ProductResponse;
import uz.onlineshop.productservice.service.ProductService;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-product")
    public ApiResult<ProductResponse> create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/update-product/{id}")
    public ApiResult<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-product/{id}")
    public ApiResult<?> delete(@PathVariable Long id) {
        return productService.delete(id);
    }

    @GetMapping("/get-product/{id}")
    public ApiResult<ProductResponse> get(@PathVariable Long id) {
        return productService.get(id);
    }

    @GetMapping("/getAll-product")
    public ApiResult<?> getAll(Pageable pageable) {
        return productService.getAll(pageable);
    }
}
