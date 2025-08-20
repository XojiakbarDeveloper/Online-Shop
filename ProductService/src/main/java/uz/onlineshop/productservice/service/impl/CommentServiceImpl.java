package uz.onlineshop.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.CommentRequest;
import uz.onlineshop.productservice.dtoes.res.CommentResponse;
import uz.onlineshop.productservice.entity.Comment;
import uz.onlineshop.productservice.mapper.CommentMapper;
import uz.onlineshop.productservice.repository.CommentRepository;
import uz.onlineshop.productservice.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public ApiResult<CommentResponse> addComment(CommentRequest request) {
        Comment comment = CommentMapper.toEntity(request);
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return ApiResult.successResponse(CommentMapper.toResponse(saved));
    }

    @Override
    public ApiResult<CommentResponse> updateComment(Long id, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setText(request.getText());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment updated = commentRepository.save(comment);
        return ApiResult.successResponse(CommentMapper.toResponse(updated));
    }

    @Override
    public ApiResult<?> deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(id);
        return ApiResult.successResponse("Comment deleted successfully");
    }

    @Override
    public ApiResult<List<CommentResponse>> getCommentsByProduct(Long productId) {
        List<CommentResponse> responses = commentRepository.findByProductId(productId)
                .stream()
                .map(CommentMapper::toResponse)
                .collect(Collectors.toList());
        return ApiResult.successResponse(responses);
    }

    @Override
    public ApiResult<List<CommentResponse>> getCommentsByUser(Long userId) {
        List<CommentResponse> responses = commentRepository.findByUserId(userId)
                .stream()
                .map(CommentMapper::toResponse)
                .collect(Collectors.toList());
        return ApiResult.successResponse(responses);
    }
}
