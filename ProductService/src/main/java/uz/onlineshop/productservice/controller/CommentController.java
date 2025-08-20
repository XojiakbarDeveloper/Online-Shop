package uz.onlineshop.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.onlineshop.authservice.config.payload.base.ApiResult;
import uz.onlineshop.productservice.dtoes.req.CommentRequest;
import uz.onlineshop.productservice.dtoes.res.CommentResponse;
import uz.onlineshop.productservice.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResult<CommentResponse> addComment(@RequestBody CommentRequest request) {
        return commentService.addComment(request);
    }

    @PutMapping("/{id}")
    public ApiResult<CommentResponse> updateComment(@PathVariable Long id,
                                         @RequestBody CommentRequest request) {
        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
    @GetMapping("/product/{productId}")
    public ApiResult<List<CommentResponse>> getCommentsByProduct(@PathVariable Long productId) {
        return commentService.getCommentsByProduct(productId);
    }

    @GetMapping("/user/{userId}")
    public ApiResult<List<CommentResponse>> getCommentsByUser(@PathVariable Long userId) {
        return commentService.getCommentsByUser(userId);
    }
}
