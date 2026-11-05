package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ReviewResponse createReview(ReviewRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (reviewRepository.findByUserAndProduct(user, product).isPresent()) {
            throw new RuntimeException("You have already reviewed this product");
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        return convertToResponse(savedReview);
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ReviewResponse> getReviewsByProductId(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByProduct(product)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ReviewResponse> getReviewsByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return reviewRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUser().getId().equals(request.getUserId())
                || !review.getProduct().getId().equals(request.getProductId())) {
            throw new RuntimeException("Review user or product does not match");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return convertToResponse(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        reviewRepository.delete(review);
    }

    private ReviewResponse convertToResponse(Review review) {

        return new ReviewResponse(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getFullName(),
                review.getProduct().getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
