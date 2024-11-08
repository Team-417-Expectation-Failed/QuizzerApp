package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.service.QuizService;

@Controller

public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String homepage(Model model) {
        model.addAttribute("quiz", quizService.findAllQuizzes());
        return "homepage";
    }

    @GetMapping("/quiz/add")
    public String addQuiz(Model model) {
        model.addAttribute("quiz", quizService.createEmptyQuiz());
        return "addquiz";
    }

    @PostMapping("/save")
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/quiz/";
    }

    @GetMapping("/edit/{id}")
    public String editQuiz(@PathVariable Long id, Model model) {
        model.addAttribute("quiz", quizService.findQuizById(id));
        return "editquiz";
    }

    @PostMapping("/update/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        quizService.saveQuiz(quiz);
        return "redirect:/quiz/";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz/";
    }
}