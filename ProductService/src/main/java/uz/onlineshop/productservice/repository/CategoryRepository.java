package uz.onlineshop.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.onlineshop.productservice.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
