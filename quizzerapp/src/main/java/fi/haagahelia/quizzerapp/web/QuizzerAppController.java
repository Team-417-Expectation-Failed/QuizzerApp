package fi.haagahelia.quizzerapp.web;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.QuestionRepository;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;

@Controller
public class QuizzerAppController {

    @Autowired
    private QuizRepository quizRepository;

    @RequestMapping(value = "/addquiz")
    public String addquiz(Model model) {
        Quiz quiz = new Quiz();
        quiz.setQuestions(new ArrayList<>());
        quiz.getQuestions().add(new Question());
        model.addAttribute("quiz", quiz);
        return "addquiz";
    }

    @RequestMapping(value = "/")
    public String homepage(Model model) {
        model.addAttribute("quiz", quizRepository.findAll());
        return "homepage";
    }

    @PostMapping("/save")
    public String save(Quiz quiz) {
        //Add a quiz where each question belongs to
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
        }
        quizRepository.save(quiz);
        return "redirect:/";
    }

    @PostMapping("/quiz/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizRepository.deleteById(id);
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

        Quiz updatedQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz Id:" + id));

        updatedQuiz.setName(quiz.getName());
        updatedQuiz.setDescription(quiz.getDescription());
        updatedQuiz.getQuestions().clear();

        for (Question question : quiz.getQuestions()) {
            question.setQuiz(updatedQuiz);
            updatedQuiz.getQuestions().add(question);
        }

        quizRepository.save(updatedQuiz);
        return "redirect:/";
    }

}