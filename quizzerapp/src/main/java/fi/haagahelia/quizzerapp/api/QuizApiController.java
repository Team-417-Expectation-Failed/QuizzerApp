package fi.haagahelia.quizzerapp.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fi.haagahelia.quizzerapp.dto.QuestionDTO;
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.domain.Question;

@RestController
@RequestMapping("/api/v2")
public class QuizApiController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizId(@PathVariable Long quizId) {
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
        
        return ResponseEntity.ok(questionDTOs);
    }
}
