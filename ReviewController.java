package com.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;
import com.ecommerce.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Create a review
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(request));
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // Get reviews by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProductId(productId));
    }

    // Get reviews by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByUserId(userId));
    }

    // Update a review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                reviewService.updateReview(reviewId, request));
    }

    // Delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}