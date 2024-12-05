package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.quizzerapp.dto.AnswerDTO;
import fi.haagahelia.quizzerapp.dto.AnswerOptionDTO;
import fi.haagahelia.quizzerapp.dto.QuestionDTO;
import fi.haagahelia.quizzerapp.dto.QuizCategoryDTO;
import fi.haagahelia.quizzerapp.dto.QuizDTO;
import fi.haagahelia.quizzerapp.repositories.AnswerRepository;
import fi.haagahelia.quizzerapp.service.AnswerService;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.service.QuizCategoryService;
import fi.haagahelia.quizzerapp.service.QuizService;
import fi.haagahelia.quizzerapp.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import fi.haagahelia.quizzerapp.domain.Answer;
import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.domain.Review;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        private AnswerRepository answerRepository;

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

                if (quizDTOs.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no quizzes found
                }

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
        public QuizDTO findQuizById(@PathVariable Long quizId) {
                Quiz quiz = quizService.findQuizById(quizId); // Returns quiz or null
                if (quiz != null) {
                        QuizDTO quizDTO = new QuizDTO(quiz.getId(), quiz.getName(), quiz.getDescription(),
                                        quiz.getCreatedDate(),
                                        quiz.isPublished(),
                                        quiz.getQuizCategory() != null ? quiz.getQuizCategory().getName()
                                                        : "Uncategorized");
                        return quizDTO; // HTTP 200
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

                List<QuestionDTO> questionDTOs = questions.stream()
                                .map(question -> new QuestionDTO(
                                                question.getId(),
                                                question.getQuestionBody(),
                                                question.getDifficultyLevel(),
                                                question.getAnswerOptions().stream()
                                                                .map(answerOption -> new AnswerOptionDTO(
                                                                                answerOption.getId(),
                                                                                answerOption.getAnswerOptionBody(),
                                                                                answerOption.isCorrect())) // Lisää
                                                                                                           // oikean
                                                                                                           // vastauksen
                                                                                                           // tieto
                                                                .collect(Collectors.toList())))
                                .collect(Collectors.toList());

                if (questionDTOs.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no questions found
                }

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

        @Operation(summary = "Create a new answer", description = "Creates a new answer for a question")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Question is not found")
        })
        @PostMapping("/answers")
        public AnswerDTO createAnswer(@RequestBody AnswerDTO answerDTO) {
                Quiz quiz = quizService.findPublishedQuizById(answerDTO.getQuizId());
                Question question = answerService.findQuestionById(answerDTO.getQuestionId());
                AnswerOption answerOption = question.getAnswerOptions().stream()
                                .filter(ao -> ao.getId().equals(answerDTO.getAnswerOptionId()))
                                .findFirst()
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Answer option not found"));
                if (quiz == null) {
                        String errorMessage = "Quiz not found with ID: " + answerDTO.getQuizId();
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage); // HTTP 404
                } else if (answerOption == null) {
                        String errorMessage = "Answer option not found with ID: " + answerDTO.getAnswerOptionId();
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage); // HTTP 404
                } else {
                        boolean correct = false;
                        // Maybe this needs to be removed:
                        if (answerOption.isCorrect()) {
                                correct = true;
                        }
                        // Create and save the answer
                        Answer answer = new Answer(question, answerOption, quiz, correct);
                        answerRepository.save(answer);
                        return answerDTO; // HTTP 200
                }
        }

        @Operation(summary = "Get all reviews of a quiz", description = "Get all reviews of a quiz by quiz id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
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

        @Operation(summary = "Deletes review", description = "Deletes review by review id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successful operation"),
                        @ApiResponse(responseCode = "404", description = "Question is not found")
        })
        @PostMapping("/reviews/{reviewId}")
        public String deleteReviewById(@RequestBody Review review) {
                reviewService.deleteReviewById(review.getId());
                return "Review deleted successfully";
        }

}
