package fi.haagahelia.quizzerapp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;
import fi.haagahelia.quizzerapp.service.QuizService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class QuizzerRestController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quizzes")
    public List<Quiz> findAllPublishedQuizzes() {
        return quizService.findAllPublishedQuizzes();
    }
}
