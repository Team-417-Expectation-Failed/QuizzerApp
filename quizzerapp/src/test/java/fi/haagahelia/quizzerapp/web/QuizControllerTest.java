package fi.haagahelia.quizzerapp.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.service.QuizCategoryService;
import fi.haagahelia.quizzerapp.service.QuizService;

public class QuizControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuizService quizService;

    @Mock
    private QuizCategoryService quizCategoryService;

    @InjectMocks
    private QuizController quizController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();
    }

    @Test
    public void testGetAllQuizzes() throws Exception {
        // Arrange
        when(quizService.findAllQuizzes()).thenReturn(Arrays.asList(new Quiz(), new Quiz()));
        when(quizCategoryService.findAllQuizCategories())
                .thenReturn(Arrays.asList(new QuizCategory(), new QuizCategory()));

        // Act
        mockMvc.perform(get("/quiz"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("quizlist"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("quizCategories"));
    }

    @Test
    public void testGetQuizById() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        when(quizService.findQuizById(1L)).thenReturn(quiz);

        // Act
        mockMvc.perform(get("/quiz/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("quizview"))
                .andExpect(model().attributeExists("quiz"));
    }

    @Test
    public void testShowAddQuizForm() throws Exception {
        // Arrange
        when(quizCategoryService.findAllQuizCategories())
                .thenReturn(Arrays.asList(new QuizCategory(), new QuizCategory()));

        // Act
        mockMvc.perform(get("/quiz/add"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("addquiz"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testCreateQuiz() throws Exception {
        // Act
        mockMvc.perform(post("/quiz")
                .param("name", "New Quiz")
                .param("description", "New Description"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));

        // Verify
        verify(quizService).saveQuiz(any(Quiz.class));
    }

    @Test
    public void testShowEditQuizForm() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        when(quizService.findQuizById(1L)).thenReturn(quiz);
        when(quizCategoryService.findAllQuizCategories())
                .thenReturn(Arrays.asList(new QuizCategory(), new QuizCategory()));

        // Act
        mockMvc.perform(get("/quiz/edit/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("editquiz"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    public void testUpdateQuiz() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/update/1")
                .param("name", "Updated Quiz")
                .param("description", "Updated Description"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));

        // Verify
        verify(quizService).updateQuiz(anyLong(), any(Quiz.class));
    }

    @Test
    public void testDeleteQuiz() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/delete/1"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));

        // Verify
        verify(quizService).deleteQuiz(1L);
    }
}