package fi.haagahelia.quizzerapp.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private QuizRepository quizRepository;

    @MockBean
    private AnswerOptionRepository answerOptionRepository;

    @Test
    public void testGetAllQuestions() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question1 = new Question();
        question1.setId(1L);
        question1.setQuestionBody("Question 1");
        question1.setQuiz(quiz);

        Question question2 = new Question();
        question2.setId(2L);
        question2.setQuestionBody("Question 2");
        question2.setQuiz(quiz);

        given(questionService.findQuestionsByQuizId(1L)).willReturn(Arrays.asList(question1, question2));
        given(questionService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/1/questions"))
                .andExpect(status().isOk())
                .andExpect(view().name("questionlist"))
                .andExpect(model().attributeExists("questions"))
                .andExpect(content().string(containsString("Question 1")))
                .andExpect(content().string(containsString("Question 2")));
    }

    @Test
    public void testViewQuestionButton() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        given(questionService.findQuestionById(1L)).willReturn(question);
        given(questionService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/1/questions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("questionview"))
                .andExpect(model().attributeExists("question"))
                .andExpect(content().string(containsString("Question 1")));
    }

    @Test
    public void testEditQuestionButton() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        given(questionService.findQuestionById(1L)).willReturn(question);
        given(questionService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/1/questions/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editquestion"))
                .andExpect(model().attributeExists("question"))
                .andExpect(content().string(containsString("Edit Question")));
    }

    @Test
    public void testShowAddQuestionForm() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        given(questionService.findQuizById(1L)).willReturn(quiz);

        mockMvc.perform(get("/quiz/1/questions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addquestion"))
                .andExpect(model().attributeExists("question"))
                .andExpect(content().string(containsString("Add Question")));
    }

    @Test
    public void testCreateQuestion() throws Exception {
        mockMvc.perform(post("/quiz/1/questions/add")
                .param("questionBody", "New Question"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Updated Question");
        question.setQuiz(quiz);

        given(questionService.findQuestionById(1L)).willReturn(question);

        mockMvc.perform(post("/quiz/1/questions/edit/1")
                .param("questionBody", "Updated Question"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        mockMvc.perform(post("/quiz/1/questions/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions"));
    }
}