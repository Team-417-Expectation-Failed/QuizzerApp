package fi.haagahelia.quizzerapp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import fi.haagahelia.quizzerapp.service.QuizService;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.domain.Quiz;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerOptionRepository answerOptionRepository;

    @Test
    public void testGetAllQuizzes() throws Exception {
        Quiz quiz1 = new Quiz();
        quiz1.setId(1L);
        quiz1.setName("Quiz 1");
        quiz1.setDescription("Description 1");

        Quiz quiz2 = new Quiz();
        quiz2.setId(2L);
        quiz2.setName("Quiz 2");
        quiz2.setDescription("Description 2");

        given(quizService.findAllQuizzes()).willReturn(Arrays.asList(quiz1, quiz2));

        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("quizlist"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(content().string(containsString("Quiz 1")))
                .andExpect(content().string(containsString("Quiz 2")));
    }

    @Test
    public void testViewQuizButton() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");

        given(quizService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("quizview"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(content().string(containsString("Quiz Details")));
    }

    @Test
    public void testEditQuizButton() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");
        quiz.setDescription("Description 1");

        given(quizService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editquiz"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(content().string(containsString("Edit Quiz")));
    }

    @Test
    public void testShowAddQuizForm() throws Exception {
        mockMvc.perform(get("/quiz/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addquiz"))
                .andExpect(content().string(containsString("<h2>Add Quiz</h2>")));
    }

    @Test
    public void testCreateQuiz() throws Exception {
        mockMvc.perform(post("/quiz")
                .param("name", "New Quiz")
                .param("description", "New Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }

    @Test
    public void testUpdateQuiz() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Updated Quiz");
        quiz.setDescription("Updated Description");

        given(quizService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(post("/quiz/update/1")
                .param("name", "Updated Quiz")
                .param("description", "Updated Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }

    @Test
    public void testDeleteQuiz() throws Exception {
        mockMvc.perform(post("/quiz/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz"));
    }
}
