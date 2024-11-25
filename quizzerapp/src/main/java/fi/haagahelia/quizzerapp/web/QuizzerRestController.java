package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.quizzerapp.dto.QuestionDTO;
import fi.haagahelia.quizzerapp.dto.QuizDTO;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.service.QuizService;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
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
                            .map(answerOption -> answerOption.getAnswerOptionBody())
                            .collect(Collectors.toList())))
            .collect(Collectors.toList());

        if (questionDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no questions found
        }

        return ResponseEntity.ok(questionDTOs); // Return HTTP 200 with question DTOs
    }
}
