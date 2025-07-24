//package uz.onlineshop.productservice.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import uz.onlineshop.authservice.config.payload.base.ApiResult;
//import uz.onlineshop.authservice.entity.User;
//import uz.onlineshop.productservice.dtoes.req.CommentRequest;
//import uz.onlineshop.productservice.dtoes.res.CommentResponse;
//import uz.onlineshop.productservice.entity.Comment;
//import uz.onlineshop.productservice.entity.Product;
//import uz.onlineshop.productservice.mapper.CommentMapper;
//import uz.onlineshop.productservice.repository.CommentRepository;
//import uz.onlineshop.productservice.repository.ProductRepository;
//import uz.onlineshop.productservice.service.CommentService;
//
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CommentServiceImpl implements CommentService {
//
//    private final CommentRepository commentRepository;
//
//    private final ProductRepository productRepository;
//
//
//    @Override
//    public ApiResult<CommentResponse> create(CommentRequest request, User user) {
//        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
//        if (optionalProduct.isEmpty()) {
//            return ApiResult.error("Product not found with id: " + request.getProductId());
//        }
//
//        Product product = optionalProduct.get();
//
//        Comment comment = Comment.builder()
//                .product(product)
//                .user(user)
//                .text(request.getText())
//                .build();
//
//        Comment saved = commentRepository.save(comment);
//
//        return ApiResult.successResponse(CommentMapper.toResponse(saved));
//    }
//
//    @Override
//    public ApiResult<List<CommentResponse>> getByProduct(Long productId) {
//        List<Comment> comments = commentRepository.findAllByProductId(productId);
//        List<CommentResponse> responseList = comments.stream()
//                .map(CommentMapper::toResponse)
//                .collect(Collectors.toList());
//
//        return ApiResult.successResponse(responseList);
//    }
//
//
//
//    @Override
//    public ApiResult<?> deleteComment(Long commentId, Long currentUserId) {
//        Optional<Comment> optionalComment = commentRepository.findById(commentId);
//
//        if (optionalComment.isEmpty()) {
//            return ApiResult.error("Comment not found");
//        }
//
//        Comment comment = optionalComment.get();
//
//        if (!comment.getUser().getId().equals(currentUserId)) {
//            return ApiResult.error("You are not authorized to delete this comment");
//        }
//
//        commentRepository.delete(comment);
//        return ApiResult.success("Comment successfully deleted");
//    }
//
//
//
//
//}
//
//
//
//
//
//
//
//
