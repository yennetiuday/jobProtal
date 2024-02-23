package com.jobx.jobPortal.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("companies/{companyId}/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<?> getAllReviews(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@PathVariable Long companyId, @RequestBody Review review) {
        boolean isReviewSaved = reviewService.createReview(companyId, review);
        if(isReviewSaved)
            return ResponseEntity.ok("Review added Successfully");
        else
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(companyId, reviewId));
    }

    @PutMapping("{reviewId}")
    public ResponseEntity<String> getReview(@PathVariable Long companyId,
                                            @PathVariable Long reviewId,
                                            @RequestBody Review review) {
        boolean isReviewUpdated = reviewService.updateReview(companyId, reviewId, review);
        if(isReviewUpdated)
            return ResponseEntity.ok("Review updated Successfully");
        else
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview(companyId, reviewId);
        if(isReviewDeleted)
            return ResponseEntity.ok("Review deleted successfully");
        else
            return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }
}