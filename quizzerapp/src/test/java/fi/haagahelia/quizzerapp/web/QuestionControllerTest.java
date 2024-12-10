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

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.service.QuestionService;

public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        when(questionService.findQuizById(1L)).thenReturn(quiz);
        when(questionService.findQuestionsByQuizId(1L)).thenReturn(Arrays.asList(new Question(), new Question()));

        // Act
        mockMvc.perform(get("/quiz/1/questions"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("questionlist"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("questions"));
    }

    @Test
    public void testGetQuestionById() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        Question question = new Question();
        question.setId(1L);
        when(questionService.findQuizById(1L)).thenReturn(quiz);
        when(questionService.findQuestionById(1L)).thenReturn(question);

        // Act
        mockMvc.perform(get("/quiz/1/questions/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("questionview"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testShowAddQuestionForm() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        when(questionService.findQuizById(1L)).thenReturn(quiz);

        // Act
        mockMvc.perform(get("/quiz/1/questions/add"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("addquestion"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testSaveNewQuestion() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/add")
                .param("questionBody", "New Question"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));

        // Verify
        verify(questionService).addQuestionToQuiz(anyLong(), any(Question.class));
    }

    @Test
    public void testShowEditQuestionForm() throws Exception {
        // Arrange
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        Question question = new Question();
        question.setId(1L);
        when(questionService.findQuizById(1L)).thenReturn(quiz);
        when(questionService.findQuestionById(1L)).thenReturn(question);

        // Act
        mockMvc.perform(get("/quiz/1/questions/edit/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("editquestion"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/edit/1")
                .param("questionBody", "Updated Question"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));

        // Verify
        verify(questionService).updateQuestion(anyLong(), any(Question.class));
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/delete/1"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));

        // Verify
        verify(questionService).deleteQuestion(1L);
    }
}