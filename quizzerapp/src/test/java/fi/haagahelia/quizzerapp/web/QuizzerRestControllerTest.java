package fi.haagahelia.quizzerapp.web;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.Review;
import fi.haagahelia.quizzerapp.dto.ReviewDTO;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.AnswerRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.repositories.ReviewRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizzerRestControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        QuizRepository quizRepository;

        @Autowired
        AnswerRepository answerRepository;

        @Autowired
        QuestionRepository questionRepository;

        @Autowired
        AnswerOptionRepository answerOptionRepository;

        @Autowired
        ReviewRepository reviewRepository;

        ObjectMapper mapper = new ObjectMapper();

        @BeforeEach
        void setUp() throws Exception {
                // Make sure that the database is empty before each test
                quizRepository.deleteAll();
                reviewRepository.deleteAll();
        }

        // ******* Tests for getting all published quizzes *************/

        @Test
        public void getAllQuizzesReturnsEmptyListWhenNoQuizzesExist() throws Exception {
                // Act
                this.mockMvc.perform(get("/api/quizzes"))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        public void getAllQuizzesReturnsListOfQuizzesWhenPublishedQuizzesExist()
                        throws Exception {
                // Arrange
                Quiz firstQuiz = new Quiz("First quiz", "First quiz description",
                                LocalDate.parse("2024-12-08"), true);
                Quiz secondQuiz = new Quiz("Second quiz", "Second quiz description",
                                LocalDate.parse("2024-12-08"), true);
                quizRepository.saveAll(List.of(firstQuiz, secondQuiz));

                // Act
                this.mockMvc.perform(get("/api/quizzes"))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)))
                                .andExpect(jsonPath("$[0].name").value("First quiz"))
                                .andExpect(jsonPath("$[0].id").value(firstQuiz.getId()))
                                .andExpect(jsonPath("$[1].name").value("Second quiz"))
                                .andExpect(jsonPath("$[1].id").value(secondQuiz.getId()));
        }

        @Test
        public void getAllQuizzesDoesNotReturnUnpublishedQuizzes() throws Exception {
                // Arrange
                Quiz firstQuiz = new Quiz("First quiz", "First quiz description",
                                LocalDate.parse("2024-12-08"), true);
                Quiz secondQuiz = new Quiz("Second quiz", "Second quiz description",
                                LocalDate.parse("2024-12-08"), false);
                quizRepository.saveAll(List.of(firstQuiz, secondQuiz));
                // Act
                this.mockMvc.perform(get("/api/quizzes"))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].name").value("First quiz"))
                                .andExpect(jsonPath("$[0].id").value(firstQuiz.getId()));
        }

        // ******* Tests for getting all questions *************/

        @Test
        public void getQuestionsByQuizIdReturnsEmptyListWhenQuizDoesNotHaveQuestions() throws Exception {

                // Arrange: save a quiz without questions to the database.
                Quiz quiz = new Quiz("First quiz", "First quiz description",
                                LocalDate.parse("2024-12-08"), true);
                quizRepository.save(quiz);
                // Act
                this.mockMvc.perform(get("/api/quizzes/" + quiz.getId() + "/questions"))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(0)));
                // response should have an empty list
        }

        @Test
        public void getQuestionsByQuizIdReturnsListOfQuestionsWhenQuizHasQuestions() throws Exception {
                // Arrange: save a quiz with a few questions with answer options to the database
                // and send a request.
                Quiz quiz = new Quiz("First quiz", "First quiz description",
                                LocalDate.parse("2024-12-08"), true);
                quizRepository.save(quiz);
                Question geoQ1 = new Question("What is the capital of France?", quiz);
                Question geoQ2 = new Question("Which country is Berlin the capital of?", quiz);
                Question historyQ1 = new Question("Who was the first president of the USA?", quiz);
                questionRepository.saveAll(java.util.List.of(geoQ1, geoQ2, historyQ1));
                AnswerOption geoA1 = new AnswerOption("Paris", true, geoQ1);
                AnswerOption geoA2 = new AnswerOption("London", false, geoQ1);
                AnswerOption geoA3 = new AnswerOption("Berlin", true, geoQ2);
                AnswerOption geoA4 = new AnswerOption("Madrid", false, geoQ2);
                AnswerOption histA1 = new AnswerOption("George Washington", true, historyQ1);
                AnswerOption histA2 = new AnswerOption("Abraham Lincoln", false, historyQ1);
                answerOptionRepository.saveAll(java.util.List.of(geoA1, geoA2, geoA3, geoA4, histA1, histA2));

                // Act
                this.mockMvc.perform(get("/api/quizzes/" + quiz.getId() + "/questions"))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(3)))
                                .andExpect(jsonPath("$[0].questionBody").value("What is the capital of France?"))
                                .andExpect(jsonPath("$[0].id").value(geoQ1.getId()))
                                .andExpect(jsonPath("$[1].questionBody")
                                                .value("Which country is Berlin the capital of?"))
                                .andExpect(jsonPath("$[1].id").value(geoQ2.getId()))
                                .andExpect(jsonPath("$[2].questionBody")
                                                .value("Who was the first president of the USA?"))
                                .andExpect(jsonPath("$[2].id").value(historyQ1.getId()))
                                .andExpect(jsonPath("$[1].answerOptions[0].answerOptionBody").value("Berlin"))
                                .andExpect(jsonPath("$[1].answerOptions[0].id").value(geoA3.getId()));
        }

        @Test
        public void getQuestionsByQuizIdReturnsErrorWhenQuizDoesNotExist() throws Exception {
                // Arrange
                // send a request without saving a quiz to the database. Then, the response
                // should have an appropriate HTTP status
                // Act
                this.mockMvc.perform(get("/api/quizzes/" + 1 + "/questions"))
                                // Assert
                                .andExpect(status().isNotFound());
        }

        // ******* Tests for getting a review by ID *************/

        @Test
        public void getReviewByIdReturnsReviewWhenReviewExists() throws Exception {
                // Arrange: save a quiz and a review to the database
                Quiz quiz = new Quiz("First quiz", "First quiz description", LocalDate.parse("2024-12-08"), true);
                quizRepository.save(quiz);
                Review review = new Review("John Doe", 5, "Excellent quiz!", quiz, LocalDate.now());
                reviewRepository.save(review);

                // Act
                this.mockMvc.perform(get("/api/reviews/" + review.getId()))
                                // Assert
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nickname").value("John Doe"))
                                .andExpect(jsonPath("$.rating").value(5))
                                .andExpect(jsonPath("$.reviewText").value("Excellent quiz!"))
                                .andExpect(jsonPath("$.quiz.id").value(quiz.getId()));
        }

        @Test
        public void getReviewByIdReturnsErrorWhenReviewDoesNotExist() throws Exception {
                // Act
                this.mockMvc.perform(get("/api/reviews/" + 1))
                                // Assert
                                .andExpect(status().isNotFound());
        }

        // ******* Tests for creating a new review *************/

        @Test
        public void createReviewCreatesNewReview() throws Exception {
                // Arrange: save a quiz to the database
                Quiz quiz = new Quiz("First quiz", "First quiz description", LocalDate.parse("2024-12-08"), true);
                quizRepository.save(quiz);

                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setNickname("Jane Doe");
                reviewDTO.setRating(4);
                reviewDTO.setReviewText("Good quiz!");
                reviewDTO.setQuizId(quiz.getId());

                // Act
                this.mockMvc.perform(post("/api/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(reviewDTO)))
                                // Assert
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.nickname").value("Jane Doe"))
                                .andExpect(jsonPath("$.rating").value(4))
                                .andExpect(jsonPath("$.reviewText").value("Good quiz!"))
                                .andExpect(jsonPath("$.quiz.id").value(quiz.getId()));
        }

        @Test
        public void createReviewReturnsErrorWhenQuizDoesNotExist() throws Exception {
                // Arrange: create a review with a non-existent quiz ID
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setNickname("Jane Doe");
                reviewDTO.setRating(4);
                reviewDTO.setReviewText("Good quiz!");
                reviewDTO.setQuizId(1L); // Non-existent quiz ID

                // Act: send a POST request to /api/reviews and try to create a review for a
                // non-existent quiz
                this.mockMvc.perform(post("/api/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(reviewDTO)))
                                // Assert
                                .andExpect(status().isNotFound());
        }

        @Test
        public void createAnswerSavesAnswerForPublishedQuiz() throws Exception {
                // Arrange: save a published quiz, question, and answer option to the database.
                Quiz quiz = new Quiz("Published quiz", "Published quiz description", LocalDate.parse("2024-12-08"),
                                true);
                quizRepository.save(quiz);
                Question question = new Question("What is the capital of Finland?", quiz);
                questionRepository.save(question);
                AnswerOption answerOption = new AnswerOption("Helsinki", true, question);
                answerOptionRepository.save(answerOption);

                // Request body for creating an answer with the answer option id
                String requestBody = "{ \"answerOptionId\": " + answerOption.getId() + " }";

                // Act: send a request to create an answer.
                this.mockMvc.perform(post("/api/answers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert: check that the response is created and contains the success message.
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Answer created successfully"));

                // Verify that the answer is saved in the database
                assertThat(answerRepository.count()).isEqualTo(1);
        }

        @Test
        public void createAnswerDoesNotSaveAnswerWithoutAnswerOption() throws Exception {
                // Arrange: save a quiz, question but without an answer option id in the
                // request.
                Quiz quiz = new Quiz("Published quiz", "Published quiz description", LocalDate.parse("2024-12-08"),
                                true);
                quizRepository.save(quiz);
                Question question = new Question("What is the capital of Finland?", quiz);
                questionRepository.save(question);

                // Request body without the answer option id
                String requestBody = "{ \"answerOptionId\": null }";

                // Act: send a request to create an answer.
                this.mockMvc.perform(post("/api/answers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert: check that the response returns Bad Request status.
                                .andExpect(status().isBadRequest());

                // Verify that no answers are saved in the database
                assertThat(answerRepository.count()).isEqualTo(0);
        }

        @Test
        public void createAnswerDoesNotSaveAnswerForNonExistingAnswerOption() throws Exception {
                // Arrange: save a quiz and question to the database but no such answer option
                // exists.
                Quiz quiz = new Quiz("Published quiz", "Published quiz description", LocalDate.parse("2024-12-08"),
                                true);
                quizRepository.save(quiz);
                Question question = new Question("What is the capital of Finland?", quiz);
                questionRepository.save(question);

                // Request body with a non-existing answer option id (assuming 999 is
                // non-existent).
                String requestBody = "{ \"answerOptionId\": 999 }";

                // Act: send a request to create an answer.
                this.mockMvc.perform(post("/api/answers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert: check that the response returns Not Found status.
                                .andExpect(status().isNotFound());

                // Verify that no answers are saved in the database
                assertThat(answerRepository.count()).isEqualTo(0);
        }

        @Test
        public void createAnswerDoesNotSaveAnswerForNonPublishedQuiz() throws Exception {
                // Arrange: save a non-published quiz and question to the database.
                Quiz quiz = new Quiz("Non-published quiz", "Non-published quiz description",
                                LocalDate.parse("2024-12-08"), false);
                quizRepository.save(quiz);
                Question question = new Question("What is the capital of Finland?", quiz);
                questionRepository.save(question);
                AnswerOption answerOption = new AnswerOption("Helsinki", true, question);
                answerOptionRepository.save(answerOption);

                // Request body with a valid answer option id
                String requestBody = "{ \"answerOptionId\": " + answerOption.getId() + " }";

                // Act: send a request to create an answer.
                this.mockMvc.perform(post("/api/answers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                // Assert: check that the response returns Forbidden status.
                                .andExpect(status().isForbidden());

                // Verify that no answers are saved in the database
                assertThat(answerRepository.count()).isEqualTo(0);
        }

}
