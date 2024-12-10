package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.quizzerapp.domain.Answer;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.domain.Review;
import fi.haagahelia.quizzerapp.dto.AnswerDTO;
import fi.haagahelia.quizzerapp.dto.AnswerOptionDTO;
import fi.haagahelia.quizzerapp.dto.QuestionDTO;
import fi.haagahelia.quizzerapp.dto.QuestionStatsDTO;
import fi.haagahelia.quizzerapp.dto.QuizCategoryDTO;
import fi.haagahelia.quizzerapp.dto.QuizDTO;
import fi.haagahelia.quizzerapp.dto.ReviewDTO;
import fi.haagahelia.quizzerapp.service.AnswerService;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.service.QuizCategoryService;
import fi.haagahelia.quizzerapp.service.QuizService;
import fi.haagahelia.quizzerapp.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Quizzer application", description = "Operations for accessing and managing quizzes")
public class QuizzerRestController {

        @Autowired
        private QuizService quizService;

        @Autowired
        private QuestionService questionService;

        @Autowired
        private QuizCategoryService quizCategoryService;

        @Autowired
        private AnswerService answerService;

        @Autowired
        private ReviewService reviewService;

        // Swagger documentation
        @Operation(summary = "Get all published quizzes", description = "Returns a list of published quizzes with id, name, description, created date, published status and category name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Quizzes are not found")
        })
        @GetMapping("/quizzes")
        public ResponseEntity<List<QuizDTO>> findAllPublishedQuizzes() {
                List<Quiz> quizzes = quizService.findAllPublishedQuizzes();

                List<QuizDTO> quizDTOs = quizzes.stream()
                                .map(quiz -> new QuizDTO(
                                                quiz.getId(),
                                                quiz.getName(),
                                                quiz.getDescription(),
                                                quiz.getCreatedDate(),
                                                quiz.isPublished(),
                                                quiz.getQuizCategory() != null ? quiz.getQuizCategory().getName()
                                                                : "Uncategorized"))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(quizDTOs); // Return HTTP 200 with quizzes
        }

        @Operation(summary = "Get all quizzes of a category", description = "Returns a list of published quizzes using a category id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Quizzes are not found")
        })
        @GetMapping("/categories/{categoryId}/quizzes")
        public ResponseEntity<List<QuizDTO>> findQuizzesByCategoryId(@PathVariable Long categoryId) {
                List<Quiz> quizzes = quizService.findQuizzesByCategoryId(categoryId);

                List<QuizDTO> quizDTOs = quizzes.stream()
                                .map(quiz -> new QuizDTO(
                                                quiz.getId(),
                                                quiz.getName(),
                                                quiz.getDescription(),
                                                quiz.getCreatedDate(),
                                                quiz.isPublished(),
                                                quiz.getQuizCategory() != null ? quiz.getQuizCategory().getName()
                                                                : "Uncategorized"))
                                .collect(Collectors.toList());

                if (quizDTOs.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no quizzes found
                }

                return ResponseEntity.ok(quizDTOs); // Return HTTP 200 with quizzes
        }

        @Operation(summary = "Get questions and answers for a quiz", description = "Returns a quiz with questions and answer options, including information about the correct answer")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Quiz is not found")
        })
        @GetMapping("/quizzes/{quizId}/full")
        public ResponseEntity<QuizDTO> getFullQuiz(@PathVariable Long quizId) {
                Quiz quiz = quizService.findQuizById(quizId); // Retrieve quiz details
                if (quiz == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // If quiz is not found, return 404
                }

                // Map questions and answer options, including information about the correct
                // answer
                List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                                .map(question -> new QuestionDTO(
                                                question.getId(),
                                                question.getQuestionBody(),
                                                question.getDifficultyLevel(),
                                                question.getAnswerOptions().stream()
                                                                .map(answerOption -> new AnswerOptionDTO(
                                                                                answerOption.getId(),
                                                                                answerOption.getAnswerOptionBody(),
                                                                                answerOption.isCorrect() // Include
                                                                                                         // information
                                                                                                         // about the
                                                                                                         // correct
                                                                                                         // answer
                                                                ))
                                                                .collect(Collectors.toList())))
                                .collect(Collectors.toList());

                // Create a QuizDTO and add questions to it
                QuizDTO quizDTO = new QuizDTO(
                                quiz.getId(),
                                quiz.getName(),
                                quiz.getDescription(),
                                quiz.getCreatedDate(),
                                quiz.isPublished(),
                                quiz.getQuizCategory() != null ? quiz.getQuizCategory().getName() : "Uncategorized");
                quizDTO.setQuestions(questionDTOs); // Add questions to the quizDTO

                return ResponseEntity.ok(quizDTO); // Return quiz and questions
        }

        @Operation(summary = "Get a quiz by id", description = "Returns a quiz with id, name, description, created date, published status and category name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Quiz is not found")
        })
        @GetMapping("/quizzes/{quizId}")
        public ResponseEntity<QuizDTO> findQuizById(@PathVariable Long quizId) {
                Quiz quiz = quizService.findQuizById(quizId); // Returns quiz or null
                if (quiz != null) {
                        QuizDTO quizDTO = new QuizDTO(quiz.getId(), quiz.getName(), quiz.getDescription(),
                                        quiz.getCreatedDate(),
                                        quiz.isPublished(),
                                        quiz.getQuizCategory() != null ? quiz.getQuizCategory().getName()
                                                        : "Uncategorized");
                        return ResponseEntity.ok(quizDTO); // HTTP 200
                } else {
                        String errorMessage = "Quiz not found with ID: " + quizId;
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage); // HTTP 404
                }
        }

        @Operation(summary = "Get questions by quiz id", description = "Returns a list of questions with id, question body, difficulty level and answer options")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Questions are not found")
        })
        @GetMapping("/quizzes/{quizId}/questions")
        public ResponseEntity<List<QuestionDTO>> findQuestionsByQuizId(@PathVariable Long quizId) {
                List<Question> questions = questionService.findQuestionsByQuizId(quizId);

                Quiz quiz = quizService.findQuizById(quizId);
                if (quiz == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(null); // Return HTTP 404 if quiz not found
                }

                List<QuestionDTO> questionDTOs = questions.stream()
                                .map(question -> new QuestionDTO(
                                                question.getId(),
                                                question.getQuestionBody(),
                                                question.getDifficultyLevel(),
                                                question.getAnswerOptions().stream()
                                                                .map(answerOption -> new AnswerOptionDTO(
                                                                                answerOption.getId(),
                                                                                answerOption.getAnswerOptionBody(),
                                                                                answerOption.isCorrect())) // Include
                                                                                                           // information
                                                                                                           // about the
                                                                                                           // correct
                                                                                                           // answer
                                                                .collect(Collectors.toList())))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(questionDTOs); // Return HTTP 200 with question DTOs
        }

        @Operation(summary = "Get all Categories", description = "Returns a list of quiz categories with id, name and description")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Categories are not found")
        })
        @GetMapping("/categories")
        public ResponseEntity<List<QuizCategoryDTO>> findAllCategories() {
                List<QuizCategory> quizCategories = quizCategoryService.findAllQuizCategories();

                if (quizCategories.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no categories found
                }

                List<QuizCategoryDTO> categoryDTOs = quizCategories.stream()
                                .map(category -> new QuizCategoryDTO(category.getId(), category.getName(),
                                                category.getDescription()))
                                .collect(Collectors.toList());

                return ResponseEntity.ok(categoryDTOs); // Return HTTP 200 with categories
        }

        @Operation(summary = "Get category by id", description = "Returns a category with id, name and description")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Category is not found")
        })
        @GetMapping("/categories/{categoryId}")
        public QuizCategoryDTO findCategoryById(@PathVariable Long categoryId) {
                QuizCategory quizCategory = quizCategoryService.findQuizCategoryById(categoryId);

                if (quizCategory != null) {
                        QuizCategoryDTO categoryDTO = new QuizCategoryDTO(quizCategory.getId(), quizCategory.getName(),
                                        quizCategory.getDescription());
                        return categoryDTO; // HTTP 200
                } else {
                        String errorMessage = "Category not found with ID: " + categoryId;
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage); // HTTP 404
                }
        }

        @Operation(summary = "Submit an answer", description = "Allows the user to submit an answer option for a question.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Answer created successfully"),
                        @ApiResponse(responseCode = "400", description = "AnswerOption ID is missing"),
                        @ApiResponse(responseCode = "404", description = "AnswerOption not found"),
                        @ApiResponse(responseCode = "403", description = "Quiz is not published")
        })
        @PostMapping("/answers")
        public ResponseEntity<String> submitAnswer(@Valid @RequestBody AnswerDTO answerDTO) {
                answerService.submitAnswer(answerDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body("Answer created successfully");
        }

        @Operation(summary = "Get all answers for a specific quiz", description = "Returns a list of answers for a quiz ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Answers are not found for quiz ID")
        })

        @GetMapping("/quizzes/{quizId}/answers")
        public ResponseEntity<List<Answer>> getAnswersByQuizId(@PathVariable Long quizId) {
                List<Answer> answers = answerService.findAnswersByQuizId(quizId);
                if (answers.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no answers found
                }
                return ResponseEntity.ok(answers); // Return HTTP 200 with answers
        }

        // GETS ALL REVIEWS OF A QUIZ
        @Operation(summary = "Get all reviews of a quiz", description = "Get all reviews of a quiz by quiz id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "404", description = "Review is not found"),
                        @ApiResponse(responseCode = "404", description = "Question is not found")
        })
        @GetMapping("/quizzes/{quizId}/reviews")
        public ResponseEntity<List<Review>> getReviewsByQuizId(@PathVariable Long quizId) {
                List<Review> reviews = reviewService.findReviewsByQuizId(quizId);
                if (reviews.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no reviews found
                }
                return ResponseEntity.ok(reviews); // Return HTTP 200 with reviews
        }

        @Operation(summary = "Get a review by id", description = "Get a review by review id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Review is not found")
        })
        @GetMapping("/reviews/{reviewId}")
        public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
                Review review = reviewService.findReviewById(reviewId);
                if (review == null) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no review found
                }
                return ResponseEntity.ok(review); // Return HTTP 200 with review
        }

        // CREATES A NEW REVIEW
        @Operation(summary = "Create a new review", description = "Create a new review for a quiz")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("/reviews")
        public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
                try {
                        Review review = reviewService.createReview(reviewDTO);
                        return new ResponseEntity<>(review, HttpStatus.CREATED);
                } catch (IllegalArgumentException e) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }

        // UPDATES REVIEW
        @Operation(summary = "Update an existing review", description = "Update the details of a review by its ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Review successfully updated"),
                        @ApiResponse(responseCode = "404", description = "Review not found")
        })
        @PutMapping("/reviews/{reviewId}/edit")
        public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                        @Valid @RequestBody Review updatedReview) {
                // Retrieve the existing review by ID
                Review existingReview = reviewService.findReviewById(reviewId);

                // Check if the review exists
                if (existingReview == null) {
                        // Return 404 Not Found if the review doesn't exist
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                // Update the fields of the existing review with the new data from the request
                // body
                existingReview.setNickname(updatedReview.getNickname());
                existingReview.setRating(updatedReview.getRating());
                existingReview.setReviewText(updatedReview.getReviewText());

                try {
                        // Save the updated review
                        Review savedReview = reviewService.saveReview(existingReview);

                        // Return the updated review with a 200 OK status
                        return ResponseEntity.ok(savedReview);
                } catch (Exception e) {
                        // Return a 500 Internal Server Error if something goes wrong
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(null);
                }
        }

        // DELETES A REVIEW
        @Operation(summary = "Deletes a review", description = "Deletes a review by review id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Question is not found")
        })
        @DeleteMapping("/reviews/{reviewId}")
        public ResponseEntity<String> deleteReviewById(@PathVariable Long reviewId) {
                try {
                        reviewService.deleteReviewById(reviewId);
                        return ResponseEntity.ok("Review deleted successfully");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Failed to delete review: " + e.getMessage());
                }
        }

        @Operation(summary = "Get number of correct and wrong answers of each question of a quiz", description = "Get number of correct and wrong answers of each question of a quiz by quiz id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Question is not found")
        })
        @GetMapping("/quizzes/{quizId}/results")
        public ResponseEntity<List<QuestionStatsDTO>> getQuizResults(@PathVariable Long quizId) {
                List<QuestionStatsDTO> results = answerService.getQuizResults(quizId);
                if (results.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no stats found
                }
                return ResponseEntity.ok(results); // Return HTTP 200 with stats
        }

}
