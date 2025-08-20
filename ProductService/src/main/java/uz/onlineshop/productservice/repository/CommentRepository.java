package uz.onlineshop.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.onlineshop.productservice.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByProductId(Long productId);

    List<Comment> findByUserId(Long userId);
}
