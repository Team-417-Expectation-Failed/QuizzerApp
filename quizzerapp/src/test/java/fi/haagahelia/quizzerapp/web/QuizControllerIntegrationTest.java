package fi.haagahelia.quizzerapp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.QuizCategoryRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

    @BeforeEach
    public void setUp() {
        quizRepository.deleteAll();
        quizCategoryRepository.deleteAll();
    }

    @Test
    public void testGetAllQuizzes() throws Exception {
        // Arrange: Save two quizzes to the database
        Quiz quiz1 = new Quiz();
        quiz1.setName("Quiz 1");
        quiz1.setDescription("Description 1");
        quizRepository.save(quiz1);

        Quiz quiz2 = new Quiz();
        quiz2.setName("Quiz 2");
        quiz2.setDescription("Description 2");
        quizRepository.save(quiz2);

        // Act: Perform a GET request to /quiz
        mockMvc.perform(get("/quiz"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("quizlist"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("quizCategories"));
    }

    @Test
    public void testGetQuizById() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a GET request to /quiz/{quizId}
        mockMvc.perform(get("/quiz/" + quiz.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("quizview"))
                .andExpect(model().attributeExists("quiz"));
    }

    @Test
    public void testShowAddQuizForm() throws Exception {
        // Act: Perform a GET request to /quiz/add
        mockMvc.perform(get("/quiz/add"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("addquiz"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testCreateQuiz() throws Exception {
        // Act: Perform a POST request to /quiz with parameters
        mockMvc.perform(post("/quiz")
                .param("name", "New Quiz")
                .param("description", "New Description"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }

    @Test
    public void testShowEditQuizForm() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a GET request to /quiz/edit/{id}
        mockMvc.perform(get("/quiz/edit/" + quiz.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("editquiz"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testUpdateQuiz() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a POST request to /quiz/update/{id} with parameters
        mockMvc.perform(post("/quiz/update/" + quiz.getId())
                .param("name", "Updated Quiz")
                .param("description", "Updated Description"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }

    @Test
    public void testDeleteQuiz() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a POST request to /quiz/delete/{id}
        mockMvc.perform(post("/quiz/delete/" + quiz.getId()))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }
}