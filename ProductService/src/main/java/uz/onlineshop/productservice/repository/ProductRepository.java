package uz.onlineshop.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.onlineshop.productservice.entity.Category;
import uz.onlineshop.productservice.entity.Product;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

}
