package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.CommentDTO;
import com.dachi.sellerapp.model.Comment;
import com.dachi.sellerapp.model.SellerRating;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.CommentRepository;
import com.dachi.sellerapp.repository.SellerRatingRepository;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final SellerRatingRepository sellerRatingRepo;

    public CommentService(CommentRepository commentRepo, UserRepository userRepo, SellerRatingRepository sellerRatingRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.sellerRatingRepo = sellerRatingRepo;
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

        comment = commentRepo.save(comment);

        // --- Update seller rating ---
        SellerRating sellerRating = sellerRatingRepo.findById(sellerId).orElse(null);

        if (sellerRating == null) {
            // First rating for this seller
            sellerRating = new SellerRating();
            sellerRating.setSellerId(sellerId);
            sellerRating.setAvgRating(rating);
            sellerRating.setCommentCount(1);
        } else {
            // Update running average
            double totalRating = sellerRating.getAvgRating() * sellerRating.getCommentCount();
            int newCount = sellerRating.getCommentCount() + 1;
            double newAvg = (totalRating + rating) / newCount;

            sellerRating.setAvgRating(newAvg);
            sellerRating.setCommentCount(newCount);
        }

        sellerRating.setUpdatedAt(LocalDateTime.now());
        sellerRatingRepo.save(sellerRating);

        return toDTO(comment);
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
        // Fetch the comment by its ID
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Verify that the requester is the author
        if (comment.getAuthor() == null || !comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("Not the author");
        }

        // Store the old rating to adjust seller's average
        int oldRating = comment.getRating();

        // Update the comment message and rating
        comment.setMessage(message);
        comment.setRating(rating);
        comment = commentRepo.save(comment);

        // --- Update seller's average rating ---
        Long sellerId = comment.getSeller().getId();
        SellerRating sellerRating = sellerRatingRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller rating not found"));

        // Adjust running average:
        // newAvg = ((oldAvg * commentCount) - oldRating + newRating) / commentCount
        double totalRating = sellerRating.getAvgRating() * sellerRating.getCommentCount();
        double newAverage = (totalRating - oldRating + rating) / sellerRating.getCommentCount();

        sellerRating.setAvgRating(newAverage);
        sellerRating.setUpdatedAt(LocalDateTime.now());

        sellerRatingRepo.save(sellerRating);

        return toDTO(comment);
    }


    public void deleteComment(Long commentId, Long authorId) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (c.getAuthor() == null || !c.getAuthor().getId().equals(authorId))
            throw new RuntimeException("Not the author");

        commentRepo.delete(c);
    }
}
