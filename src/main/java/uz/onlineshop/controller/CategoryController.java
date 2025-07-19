package uz.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.config.payload.base.ApiResult;
import uz.onlineshop.dtoes.req.CategoryRequest;
import uz.onlineshop.dtoes.res.CategoryResponse;
import uz.onlineshop.dtoes.res.ProductResponse;
import uz.onlineshop.service.CategoryService;
import uz.onlineshop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.get(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResult<CategoryResponse>> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request));
    }


    @GetMapping
    public ResponseEntity<ApiResult<Page<CategoryResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryResponse>> update(@PathVariable Long id,
                                                               @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> products = categoryService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
