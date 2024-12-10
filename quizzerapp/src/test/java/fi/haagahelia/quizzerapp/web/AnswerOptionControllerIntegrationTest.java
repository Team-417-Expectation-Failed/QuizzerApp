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

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerOptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @BeforeEach
    public void setUp() {
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        quizRepository.deleteAll();
    }

    @Test
    public void testGetAllAnswerOptions() throws Exception {
        // Arrange: Save a quiz, a question, and two answer options to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        AnswerOption answerOption1 = new AnswerOption();
        answerOption1.setAnswerOptionBody("Answer Option 1");
        answerOption1.setCorrect(true);
        answerOption1.setQuestion(question);
        answerOptionRepository.save(answerOption1);

        AnswerOption answerOption2 = new AnswerOption();
        answerOption2.setAnswerOptionBody("Answer Option 2");
        answerOption2.setCorrect(false);
        answerOption2.setQuestion(question);
        answerOptionRepository.save(answerOption2);

        // Act: Perform a GET request to /quiz/{quizId}/questions/{questionId}/answers
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionlist"))
                .andExpect(model().attributeExists("question"))
                .andExpect(model().attributeExists("answerOptions"));
    }

    @Test
    public void testGetAnswerOptionById() throws Exception {
        // Arrange: Save a quiz, a question, and an answer option to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);
        answerOption = answerOptionRepository.save(answerOption);

        // Act: Perform a GET request to
        // /quiz/{quizId}/questions/{questionId}/answers/{answerOptionId}
        mockMvc.perform(
                get("/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/" + answerOption.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("answeroptionview"))
                .andExpect(model().attributeExists("answerOption"));
    }

    @Test
    public void testShowAddAnswerOptionForm() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a GET request to
        // /quiz/{quizId}/questions/{questionId}/answers/add
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/add"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("addansweroption"))
                .andExpect(model().attributeExists("newAnswerOption"));
    }

    @Test
    public void testSaveNewAnswerOption() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a POST request to
        // /quiz/{quizId}/questions/{questionId}/answers/add with parameters
        mockMvc.perform(post("/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/add")
                .param("answerOptionBody", "New Answer Option")
                .param("correct", "true"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(
                        view().name("redirect:/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers"));
    }

    @Test
    public void testShowEditAnswerOptionForm() throws Exception {
        // Arrange: Save a quiz, a question, and an answer option to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);
        answerOption = answerOptionRepository.save(answerOption);

        // Act: Perform a GET request to
        // /quiz/{quizId}/questions/{questionId}/answers/edit/{id}
        mockMvc.perform(get(
                "/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/edit/" + answerOption.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("editansweroption"))
                .andExpect(model().attributeExists("answerOption"));
    }

    @Test
    public void testUpdateAnswerOption() throws Exception {
        // Arrange: Save a quiz, a question, and an answer option to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);
        answerOption = answerOptionRepository.save(answerOption);

        // Act: Perform a POST request to
        // /quiz/{quizId}/questions/{questionId}/answers/edit/{id} with parameters
        mockMvc.perform(post(
                "/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/edit/" + answerOption.getId())
                .param("answerOptionBody", "Updated Answer Option")
                .param("correct", "true"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(
                        view().name("redirect:/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers"));
    }

    @Test
    public void testDeleteAnswerOption() throws Exception {
        // Arrange: Save a quiz, a question, and an answer option to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        AnswerOption answerOption = new AnswerOption();
        answerOption.setAnswerOptionBody("Answer Option 1");
        answerOption.setCorrect(true);
        answerOption.setQuestion(question);
        answerOption = answerOptionRepository.save(answerOption);

        // Act: Perform a POST request to
        // /quiz/{quizId}/questions/{questionId}/answers/delete/{id}
        mockMvc.perform(post(
                "/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers/delete/" + answerOption.getId()))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(
                        view().name("redirect:/quiz/" + quiz.getId() + "/questions/" + question.getId() + "/answers"));
    }
}