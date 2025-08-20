package uz.onlineshop.productservice.service;

import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.CommentRequest;
import uz.onlineshop.productservice.dtoes.res.CommentResponse;

import java.util.List;

public interface CommentService {

    ApiResult<CommentResponse> addComment(CommentRequest request);

    ApiResult<CommentResponse> updateComment(Long id, CommentRequest request);

    ApiResult<?> deleteComment(Long id);

    ApiResult<List<CommentResponse>> getCommentsByProduct(Long productId);

    ApiResult<List<CommentResponse>> getCommentsByUser(Long userId);
}
