//package uz.onlineshop.productservice.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import uz.onlineshop.authservice.entity.User;
//import uz.onlineshop.productservice.dtoes.req.CommentRequest;
//import uz.onlineshop.productservice.dtoes.res.CommentResponse;
//import uz.onlineshop.productservice.service.CommentService;
//import uz.onlineshop.authservice.config.payload.base.ApiResult;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/comments")
//@RequiredArgsConstructor
//public class CommentController {
//
//    private final CommentService commentService;
//
//    // Comment yaratish
//    @PostMapping
//    public ApiResult<CommentResponse> createComment(@RequestBody CommentRequest request,
//                                                    @AuthenticationPrincipal User user) {
//        return commentService.create(request, user);
//    }
//
//    // Mahsulot bo'yicha commentlarni olish
//    @GetMapping
//    public ApiResult<List<CommentResponse>> getCommentsByProduct(@RequestParam Long productId) {
//        return commentService.getByProduct(productId);
//    }
//
//    // Commentni o'chirish
//    @DeleteMapping
//    public ApiResult<?> deleteComment(@RequestParam Long commentId,
//                                      @RequestParam Long currentUserId) {
//        return commentService.deleteComment(commentId, currentUserId);
//    }
//}
