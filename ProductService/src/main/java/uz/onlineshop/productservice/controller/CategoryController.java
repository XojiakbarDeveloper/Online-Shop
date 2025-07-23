package uz.onlineshop.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.CategoryRequest;
import uz.onlineshop.productservice.dtoes.res.CategoryResponse;
import uz.onlineshop.productservice.dtoes.res.ProductResponse;
import uz.onlineshop.productservice.service.CategoryService;


import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get-category")
    public ResponseEntity<ApiResult<CategoryResponse>> get(@RequestParam("id") Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-category")
    public ResponseEntity<ApiResult<CategoryResponse>> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request));
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResult<Page<CategoryResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-category")
    public ResponseEntity<ApiResult<CategoryResponse>> update(@RequestParam("id") Long id,
                                                               @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-category")
    public ResponseEntity<ApiResult<Void>> delete(@RequestParam("id") Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/by-category-product}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@RequestParam("id") Long categoryId) {
        List<ProductResponse> products = categoryService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
