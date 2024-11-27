package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fi.haagahelia.quizzerapp.dto.AnswerOptionDTO;
import fi.haagahelia.quizzerapp.dto.QuestionDTO;
import fi.haagahelia.quizzerapp.dto.QuizDTO;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.service.QuizService;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizzerRestController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    // Get all published quizzes
    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizDTO>> findAllPublishedQuizzes() {
        List<Quiz> quizzes = quizService.findAllPublishedQuizzes();

        List<QuizDTO> quizDTOs = quizzes.stream()
                .map(quiz -> new QuizDTO(
                        quiz.getId(),
                        quiz.getName(),
                        quiz.getDescription(),
                        quiz.getCreatedDate(),
                        quiz.isPublished(),
                        quiz.getQuizCategory().getName()))
                .collect(Collectors.toList());

        if (quizDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no quizzes found
        }

        return ResponseEntity.ok(quizDTOs); // Return HTTP 200 with quizzes
    }

    // Get quiz by id
    @GetMapping("/quizzes/{quizId}")
    public Quiz findQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.findQuizById(quizId); // Returns quiz or null
        if (quiz != null) {
            return quiz; // HTTP 200
        } else {
            String errorMessage = "Quiz not found with ID: " + quizId;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage); // HTTP 404
        }
    }

    // Get questions and answers for a quiz (new method)
    @GetMapping("/quizzes/{quizId}/full")
    public ResponseEntity<QuizDTO> getFullQuiz(@PathVariable Long quizId) {
        Quiz quiz = quizService.findQuizById(quizId); // Retrieve quiz details
        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // If quiz is not found, return 404
        }

        // Map questions and answer options, including information about the correct
        // answer
        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getQuestionBody(),
                        question.getDifficultyLevel(),
                        question.getAnswerOptions().stream()
                                .map(answerOption -> new AnswerOptionDTO(
                                        answerOption.getAnswerOptionBody(),
                                        answerOption.isCorrect() // Include information about the correct answer
                                ))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        // Create a QuizDTO and add questions to it
        QuizDTO quizDTO = new QuizDTO(
                quiz.getId(),
                quiz.getName(),
                quiz.getDescription(),
                quiz.getCreatedDate(),
                quiz.isPublished(),
                quiz.getQuizCategory().getName());
        quizDTO.setQuestions(questionDTOs); // Add questions to the quizDTO

        return ResponseEntity.ok(quizDTO); // Return quiz and questions
    }

    // Get questions by quiz id
    @GetMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> findQuestionsByQuizId(@PathVariable Long quizId) {
        List<Question> questions = questionService.findQuestionsByQuizId(quizId);

        List<QuestionDTO> questionDTOs = questions.stream()
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getQuestionBody(),
                        question.getDifficultyLevel(),
                        question.getAnswerOptions().stream()
                                .map(answerOption -> new AnswerOptionDTO(
                                        answerOption.getAnswerOptionBody(),
                                        answerOption.isCorrect())) // Lisää oikean vastauksen tieto
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        if (questionDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no questions found
        }

        return ResponseEntity.ok(questionDTOs); // Return HTTP 200 with question DTOs
    }
}
