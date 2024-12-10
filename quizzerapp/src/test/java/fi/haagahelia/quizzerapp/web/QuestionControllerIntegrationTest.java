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

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    public void setUp() {
        questionRepository.deleteAll();
        quizRepository.deleteAll();
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        // Arrange: Save a quiz and two questions to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question1 = new Question();
        question1.setQuestionBody("Question 1");
        question1.setQuiz(quiz);
        questionRepository.save(question1);

        Question question2 = new Question();
        question2.setQuestionBody("Question 2");
        question2.setQuiz(quiz);
        questionRepository.save(question2);

        // Act: Perform a GET request to /quiz/{quizId}/questions
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("questionlist"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("questions"));
    }

    @Test
    public void testGetQuestionById() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a GET request to /quiz/{quizId}/questions/{questionId}
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions/" + question.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("questionview"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testShowAddQuestionForm() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a GET request to /quiz/{quizId}/questions/add
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions/add"))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("addquestion"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testSaveNewQuestion() throws Exception {
        // Arrange: Save a quiz to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        // Act: Perform a POST request to /quiz/{quizId}/questions/add with parameters
        mockMvc.perform(post("/quiz/" + quiz.getId() + "/questions/add")
                .param("questionBody", "New Question"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/" + quiz.getId() + "/questions"));
    }

    @Test
    public void testShowEditQuestionForm() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a GET request to /quiz/{quizId}/questions/edit/{id}
        mockMvc.perform(get("/quiz/" + quiz.getId() + "/questions/edit/" + question.getId()))
                // Assert: Verify the status, view name, and model attributes
                .andExpect(status().isOk())
                .andExpect(view().name("editquestion"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("question"));
    }

    @Test
    public void testUpdateQuestion() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a POST request to /quiz/{quizId}/questions/edit/{id} with
        // parameters
        mockMvc.perform(post("/quiz/" + quiz.getId() + "/questions/edit/" + question.getId())
                .param("questionBody", "Updated Question"))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/" + quiz.getId() + "/questions"));
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        // Arrange: Save a quiz and a question to the database
        Quiz quiz = new Quiz();
        quiz.setName("Quiz 1");
        quiz = quizRepository.save(quiz);

        Question question = new Question();
        question.setQuestionBody("Question 1");
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        // Act: Perform a POST request to /quiz/{quizId}/questions/delete/{id}
        mockMvc.perform(post("/quiz/" + quiz.getId() + "/questions/delete/" + question.getId()))
                // Assert: Verify the status and view name
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/quiz/" + quiz.getId() + "/questions"));
    }
}