package uz.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.onlineshop.entity.Category;
import uz.onlineshop.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

}
