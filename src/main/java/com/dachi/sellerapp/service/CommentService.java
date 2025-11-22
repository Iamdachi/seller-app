package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.CommentDTO;
import com.dachi.sellerapp.model.Comment;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.CommentRepository;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final UserRepository userRepo;

    public CommentService(CommentRepository commentRepo, UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }

    private CommentDTO toDTO(Comment c) {
        return new CommentDTO(
                c.getId(),
                c.getAuthor() != null ? c.getAuthor().getId() : null,
                c.getSeller().getId(),
                c.getMessage(),
                c.getRating(),
                c.getCreatedAt()
        );
    }

    public CommentDTO addComment(Long sellerId, Long authorId, String message, int rating) {
        User seller = userRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        User author = null;
        if (authorId != null) {
            author = userRepo.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
        }

        Comment comment = new Comment();
        comment.setSeller(seller);
        comment.setAuthor(author);
        comment.setMessage(message);
        comment.setRating(rating);
        comment.setCreatedAt(LocalDateTime.now());

        return toDTO(commentRepo.save(comment));
    }

    public List<CommentDTO> getSellerComments(Long sellerId) {
        return commentRepo.findBySellerId(sellerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getComment(Long sellerId, Long commentId) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!c.getSeller().getId().equals(sellerId)) throw new RuntimeException("Seller mismatch");
        return toDTO(c);
    }

    public CommentDTO updateComment(Long commentId, Long authorId, String message, int rating) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (c.getAuthor() == null || !c.getAuthor().getId().equals(authorId))
            throw new RuntimeException("Not the author");

        c.setMessage(message);
        c.setRating(rating);

        return toDTO(commentRepo.save(c));
    }

    public void deleteComment(Long commentId, Long authorId) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (c.getAuthor() == null || !c.getAuthor().getId().equals(authorId))
            throw new RuntimeException("Not the author");

        commentRepo.delete(c);
    }
}
