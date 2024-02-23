package com.jobx.jobPortal.review.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.jobx.jobPortal.company.Company;
import com.jobx.jobPortal.company.CompanyService;
import com.jobx.jobPortal.review.Review;
import com.jobx.jobPortal.review.ReviewRepository;
import com.jobx.jobPortal.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        Company company = companyService.getCompanyById(companyId);
        if(Objects.nonNull(company)) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
        } else return false;
    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        Company company = companyService.getCompanyById(companyId);
        if(Objects.nonNull(company)) {
            updatedReview.setCompany(company);
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        } else
            return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(Objects.nonNull(companyService.getCompanyById(companyId))
                && reviewRepository.existsById(reviewId)) {
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReviews().remove(review);
            companyService.updateCompany(company, companyId);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
