package fi.haagahelia.quizzerapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.Review;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.repositories.ReviewRepository;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private QuizRepository quizRepository;

    // gets the quiz by its id
    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    // gets all reviews for a specific quiz by quiz id
    public List<Review> findReviewsByQuizId(Long quizId) {
        return reviewRepository.findByQuizId(quizId);
    }

    // finds review by its id
    public Review findReviewById(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        return review.orElse(null);  // Return null if not found
    }

    // deletes review by its id
    public void deleteReviewById(Long quizId) {
        reviewRepository.deleteById(quizId);
    }

    // saves review
    public Review saveReview(Review review) {
        // Find the quiz by id
        Quiz quiz = quizRepository.findById(review.getQuiz().getId())
            .orElseThrow(() -> new RuntimeException("Quiz not found"));
        
        // Set the quiz on the review
        review.setQuiz(quiz);

        // If the review date is not provided, set the current date
        if (review.getReviewDate() == null) {
            review.setReviewDate(LocalDate.now());  // Set current date if not provided
        }

        return reviewRepository.save(review);  // Save the review
    }
    

}
