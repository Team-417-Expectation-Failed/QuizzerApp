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

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.service.AnswerOptionService;

public class AnswerOptionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnswerOptionService answerOptionService;

    @InjectMocks
    private AnswerOptionController answerOptionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(answerOptionController).build();
    }

    @Test
    public void testGetAllAnswerOptions() throws Exception {
        // Arrange
        Question question = new Question();
        question.setId(1L);
        when(answerOptionService.findQuestionById(1L)).thenReturn(question);
        when(answerOptionService.findAllAnswerOptionsByQuestionId(1L))
                .thenReturn(Arrays.asList(new AnswerOption(), new AnswerOption()));

        // Act
        mockMvc.perform(get("/quiz/1/questions/1/answers"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionlist"))
                .andExpect(model().attributeExists("question"))
                .andExpect(model().attributeExists("answerOptions"));
    }

    @Test
    public void testGetAnswerOptionById() throws Exception {
        // Arrange
        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(1L);
        when(answerOptionService.findAnswerOptionById(1L)).thenReturn(answerOption);

        // Act
        mockMvc.perform(get("/quiz/1/questions/1/answers/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionview"))
                .andExpect(model().attributeExists("answerOption"));
    }

    @Test
    public void testShowAddAnswerOptionForm() throws Exception {
        // Arrange
        Question question = new Question();
        question.setId(1L);
        when(answerOptionService.findQuestionById(1L)).thenReturn(question);

        // Act
        mockMvc.perform(get("/quiz/1/questions/1/answers/add"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("addansweroption"))
                .andExpect(model().attributeExists("newAnswerOption"));
    }

    @Test
    public void testSaveNewAnswerOption() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/1/answers/add")
                .param("answerOptionBody", "New Answer Option")
                .param("correct", "true"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        // Verify
        verify(answerOptionService).addAnswerOptionToQuestion(anyLong(), any(AnswerOption.class));
    }

    @Test
    public void testShowEditAnswerOptionForm() throws Exception {
        // Arrange
        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(1L);
        when(answerOptionService.findAnswerOptionById(1L)).thenReturn(answerOption);

        // Act
        mockMvc.perform(get("/quiz/1/questions/1/answers/edit/1"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(view().name("editansweroption"))
                .andExpect(model().attributeExists("answerOption"));
    }

    @Test
    public void testUpdateAnswerOption() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/1/answers/edit/1")
                .param("answerOptionBody", "Updated Answer Option")
                .param("correct", "true"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        // Verify
        verify(answerOptionService).updateAnswerOption(anyLong(), any(AnswerOption.class));
    }

    @Test
    public void testDeleteAnswerOption() throws Exception {
        // Act
        mockMvc.perform(post("/quiz/1/questions/1/answers/delete/1"))
                // Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        // Verify
        verify(answerOptionService).deleteAnswerOption(1L);
    }
}