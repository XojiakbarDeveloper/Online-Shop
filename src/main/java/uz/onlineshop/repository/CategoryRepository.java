package uz.onlineshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.onlineshop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
