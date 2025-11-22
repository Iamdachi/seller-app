package com.dachi.sellerapp.controller;

import com.dachi.sellerapp.dto.CommentDTO;
import com.dachi.sellerapp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users/{sellerId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST /users/:id/comments
    @PostMapping
    public CommentDTO addComment(@PathVariable Long sellerId,
                                 @RequestBody Map<String, String> body) {
        Long authorId = body.containsKey("authorId") ? Long.valueOf(body.get("authorId")) : null;
        String message = body.get("message");
        int rating = Integer.parseInt(body.get("rating"));
        return commentService.addComment(sellerId, authorId, message, rating);
    }

    // GET /users/:id/comments
    @GetMapping
    public List<CommentDTO> getComments(@PathVariable Long sellerId) {
        return commentService.getSellerComments(sellerId);
    }

    // GET /users/:id/comments/:id
    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable Long sellerId,
                                 @PathVariable Long commentId) {
        return commentService.getComment(sellerId, commentId);
    }

    // PUT /users/:id/comments
    @PutMapping
    public CommentDTO updateComment(@RequestBody Map<String, String> body) {
        Long commentId = Long.valueOf(body.get("commentId"));
        Long authorId = Long.valueOf(body.get("authorId"));
        String message = body.get("message");
        int rating = Integer.parseInt(body.get("rating"));

        return commentService.updateComment(commentId, authorId, message, rating);
    }

    // DELETE /users/:id/comments/:id
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long sellerId,
                                @PathVariable Long commentId,
                                @RequestParam Long authorId) {
        commentService.deleteComment(commentId, authorId);
        return "deleted";
    }
}
