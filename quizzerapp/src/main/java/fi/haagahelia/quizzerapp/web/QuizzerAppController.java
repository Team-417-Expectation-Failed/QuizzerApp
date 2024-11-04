package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;

@Controller
public class QuizzerAppController {

    @Autowired
    private QuizRepository quizRepository;

    @RequestMapping(value = "/homepage")
    public String homePage(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "homepage";
    }

    @GetMapping("/quizzes")
    @ResponseBody
    public Iterable<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }
}