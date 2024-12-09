package fi.haagahelia.quizzerapp.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.AnswerRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizzerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AnswerRepository answerRepository;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        // Make sure that the database is empty before each test
        quizRepository.deleteAll();
    }

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

}
