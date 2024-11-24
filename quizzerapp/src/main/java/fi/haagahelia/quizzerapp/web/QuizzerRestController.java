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
import fi.haagahelia.quizzerapp.service.QuestionService;
import fi.haagahelia.quizzerapp.domain.Question;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class QuizzerRestController {


    @Autowired
    private QuestionService questionService;

    // Get questions by quiz id
    @GetMapping("/quizzes/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> findQuestionsByQuizId(@PathVariable Long quizId) {
        List<Question> questions = questionService.findQuestionsByQuizId(quizId);

        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestionBody(question.getQuestionBody());
            questionDTOs.add(questionDTO);
        }

        if (questionDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if no questions found
        }

        return ResponseEntity.ok(questionDTOs); // Return HTTP 200 with question DTOs
    }
}
