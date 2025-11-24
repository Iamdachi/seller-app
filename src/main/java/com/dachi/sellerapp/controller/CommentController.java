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

    /**
     * POST /users/{sellerId}/comments
     * Submit a new comment for a seller (author may be anonymous).
     */
    @PostMapping
    public CommentDTO addComment(
            @PathVariable("sellerId") Long sellerId,
            @RequestBody CommentDTO commentDTO
    ) {
        // Extract fields from DTO
        Long authorId = commentDTO.authorId(); // can be null
        String message = commentDTO.message();
        int rating = commentDTO.rating();

        return commentService.addComment(sellerId, authorId, message, rating);
    }

    /**
     * GET /users/{sellerId}/comments
     * Retrieve all comments for a seller.
     */
    @GetMapping
    public List<CommentDTO> getComments(@PathVariable Long sellerId) {
        return commentService.getSellerComments(sellerId);
    }

    /**
     * GET /users/{sellerId}/comments/{commentId}
     * Retrieve a specific comment.
     */
    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable Long sellerId,
                                 @PathVariable Long commentId) {
        return commentService.getComment(sellerId, commentId);
    }

    /**
     * PUT /users/{sellerId}/comments
     * Update an existing comment (only author can update).
     */
    @PutMapping
    public CommentDTO updateComment(@RequestBody Map<String, String> body) {
        Long commentId = Long.valueOf(body.get("commentId"));
        Long authorId = Long.valueOf(body.get("authorId"));
        String message = body.get("message");
        int rating = Integer.parseInt(body.get("rating"));

        return commentService.updateComment(commentId, authorId, message, rating);
    }

    /**
     * DELETE /users/{sellerId}/comments/{commentId}
     * Delete a comment (only author can delete).
     */
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long sellerId,
                                @PathVariable Long commentId,
                                @RequestParam Long authorId) {
        commentService.deleteComment(commentId, authorId);
        return "deleted";
    }
}
