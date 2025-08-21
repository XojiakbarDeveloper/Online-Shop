package uz.onlineshop.productservice.mapper;


import uz.onlineshop.productservice.dtoes.req.CommentRequest;
import uz.onlineshop.productservice.dtoes.res.CommentResponse;
import uz.onlineshop.productservice.entity.Comment;

import java.time.LocalDateTime;

public interface CommentMapper {

    static Comment toEntity(CommentRequest request) {
        return Comment.builder()
                .productId(request.getProductId())
                .userId(request.getUserId())
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();
    }

    static CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .productId(comment.getProductId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
