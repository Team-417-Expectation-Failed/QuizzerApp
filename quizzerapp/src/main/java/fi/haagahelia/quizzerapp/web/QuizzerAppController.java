package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;

@Controller
public class QuizzerAppController {

    @Autowired
    private QuizRepository quizRepository;

    @RequestMapping(value = "/addquiz")
    public String addquiz(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "addquiz";
    }

    @RequestMapping(value = "/")
    public String homepage(Model model) {
        model.addAttribute("quiz", quizRepository.findAll());
        return "homepage";
    }

    @PostMapping("/save")
    public String save(Quiz quiz) {
        quizRepository.save(quiz);
        return "redirect:/";
    }

    @GetMapping("/quizzes")
    @ResponseBody
    public Iterable<Quiz> getQuizzes() {
        return quizRepository.findAll();
    }

    // Edit quiz

    @GetMapping("/edit/{id}")
    public String showUpdateQuizForm(@PathVariable("id") long id, Model model) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID:" + id));
        model.addAttribute("quiz", quiz);
        return "editquiz";
    }

    @PostMapping("/update/{id}")
    public String updateQuiz(@PathVariable("id") long id, Quiz quiz,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            quiz.setId(id);
            return "editquiz";
        }

        quizRepository.save(quiz);
        return "redirect:/";
    }

}