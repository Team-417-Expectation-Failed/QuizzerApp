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

import fi.haagahelia.quizzerapp.service.AnswerOptionService;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@WebMvcTest(AnswerOptionController.class)
public class AnswerOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AnswerOptionService answerOptionService;

    @Mock
    private AnswerOptionRepository answerOptionRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Test
    public void testGetAllAnswerOptions() throws Exception {
        System.out.println("Running testGetAllAnswerOptions");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        AnswerOption answerOption1 = new AnswerOption();
        answerOption1.setId(1L);
        answerOption1.setAnswerOptionBody("Answer Option 1");
        answerOption1.setCorrect(true);
        answerOption1.setQuestion(question);

        AnswerOption answerOption2 = new AnswerOption();
        answerOption2.setId(2L);
        answerOption2.setAnswerOptionBody("Answer Option 2");
        answerOption2.setCorrect(false);
        answerOption2.setQuestion(question);

        given(answerOptionService.findAllAnswerOptionsByQuestionId(1L)).willReturn(Arrays.asList(answerOption1, answerOption2));
        given(answerOptionService.findQuestionById(1L)).willReturn(question);

        mockMvc.perform(get("/quiz/1/questions/1/answers"))
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionlist"))
                .andExpect(model().attributeExists("answerOptions"))
                .andExpect(content().string(containsString("Answer Option 1")))
                .andExpect(content().string(containsString("Answer Option 2")));

        System.out.println("Completed testGetAllAnswerOptions");
    }

    @Test
    public void testViewAnswerOptionButton() throws Exception {
        System.out.println("Running testViewAnswerOptionButton");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(1L);
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);

        given(answerOptionService.findAnswerOptionById(1L)).willReturn(answerOption);
        given(answerOptionService.findQuestionById(1L)).willReturn(question);

        mockMvc.perform(get("/quiz/1/questions/1/answers/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionview"))
                .andExpect(model().attributeExists("answerOption"))
                .andExpect(content().string(containsString("Answer Option 1")));

        System.out.println("Completed testViewAnswerOptionButton");
    }

    @Test
    public void testEditAnswerOptionButton() throws Exception {
        System.out.println("Running testEditAnswerOptionButton");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(1L);
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);

        given(answerOptionService.findAnswerOptionById(1L)).willReturn(answerOption);
        given(answerOptionService.findQuestionById(1L)).willReturn(question);

        mockMvc.perform(get("/quiz/1/questions/1/answers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editansweroption"))
                .andExpect(model().attributeExists("answerOption"))
                .andExpect(content().string(containsString("Edit Answer Option")));

        System.out.println("Completed testEditAnswerOptionButton");
    }

    @Test
    public void testShowAddAnswerOptionForm() throws Exception {
        System.out.println("Running testShowAddAnswerOptionForm");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        given(answerOptionService.findQuestionById(1L)).willReturn(question);

        mockMvc.perform(get("/quiz/1/questions/1/answers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addansweroption"))
                .andExpect(model().attributeExists("answerOption"))
                .andExpect(content().string(containsString("Add Answer Option")));

        System.out.println("Completed testShowAddAnswerOptionForm");
    }

    @Test
    public void testCreateAnswerOption() throws Exception {
        System.out.println("Running testCreateAnswerOption");

        mockMvc.perform(post("/quiz/1/questions/1/answers/add")
                .param("answerOptionBody", "New Answer Option")
                .param("correct", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        System.out.println("Completed testCreateAnswerOption");
    }

    @Test
    public void testUpdateAnswerOption() throws Exception {
        System.out.println("Running testUpdateAnswerOption");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Quiz 1");

        Question question = new Question();
        question.setId(1L);
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setId(1L);
        answerOption.setAnswerOptionBody("Updated Answer Option");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);

        given(answerOptionService.findAnswerOptionById(1L)).willReturn(answerOption);

        mockMvc.perform(post("/quiz/1/questions/1/answers/edit/1")
                .param("answerOptionBody", "Updated Answer Option")
                .param("correct", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        System.out.println("Completed testUpdateAnswerOption");
    }

    @Test
    public void testDeleteAnswerOption() throws Exception {
        System.out.println("Running testDeleteAnswerOption");

        mockMvc.perform(post("/quiz/1/questions/1/answers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/1/questions/1/answers"));

        System.out.println("Completed testDeleteAnswerOption");
    }
}