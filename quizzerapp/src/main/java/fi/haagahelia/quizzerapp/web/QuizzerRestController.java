package fi.haagahelia.quizzerapp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.service.QuizService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizzerRestController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/quizzes")
    public List<Quiz> findAllPublishedQuizzes() {
        return quizService.findAllPublishedQuizzes();
    }

    // Get quiz by id using ResponseEntity to handle exceptions
    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<Object> findQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.findQuizById(quizId); // Returns quiz or null
        if (quiz != null) {
            return ResponseEntity.ok(quiz); // HTTP 200
        } else {
            String errorMessage = "Quiz not found with ID: " + quizId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage); // HTTP 404
        }
    }

    @GetMapping("/quizzes/{quizId}/questions")
    public List<Question> findQuestionsByQuizId(@PathVariable Long quizId) {
        return questionService.findQuestionsByQuizId(quizId);
    }
}
