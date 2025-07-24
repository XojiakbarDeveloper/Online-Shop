//package uz.onlineshop.productservice.service;
//
//import uz.onlineshop.authservice.entity.User;
//import uz.onlineshop.authservice.config.payload.base.ApiResult;
//import uz.onlineshop.productservice.dtoes.req.CommentRequest;
//import uz.onlineshop.productservice.dtoes.res.CommentResponse;
//
//import java.util.List;
//
//public interface CommentService {
//
//    ApiResult<CommentResponse> create(CommentRequest request, User user);
//
//    ApiResult<List<CommentResponse>> getByProduct(Long productId);
//
//    ApiResult<?> deleteComment(Long commentId, Long currentUserId);
//}
