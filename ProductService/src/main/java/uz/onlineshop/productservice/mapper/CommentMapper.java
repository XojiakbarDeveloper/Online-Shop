package uz.onlineshop.productservice.mapper;

import uz.onlineshop.productservice.dtoes.res.CommentResponse;
import uz.onlineshop.productservice.entity.Comment;

public interface CommentMapper {

    public static CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .productId(comment.getProduct().getId())
                .build();
    }
}
