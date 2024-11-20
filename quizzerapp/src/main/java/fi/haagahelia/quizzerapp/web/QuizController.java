package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.service.QuizCategoryService;
import fi.haagahelia.quizzerapp.service.QuizService;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizCategoryService quizCategoryService;

    // Get all quizzes
    @GetMapping("/quiz")
    public String getAllQuizzes(Model model) {
        model.addAttribute("quizzes", quizService.findAllQuizzes());
        return "quizlist"; // Render a view for all quizzes
    }

    // Redirect to home page
    @GetMapping
    public String getHomePage() {
        return "redirect:/quiz";
    }

    // Get quiz by id
    @GetMapping("/quiz/{quizId}")
    public String getQuizById(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", quizService.findQuizById(quizId));
        return "quizview"; // Render a view for a specific quiz
    }

    // Get quiz add view
    @GetMapping("/quiz/add")
    public String showAddQuizForm(Model model) {
        Quiz quiz = new Quiz();
        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", quizCategoryService.findAllQuizCategories());
        return "addquiz"; // Render a view for adding a quiz
    }

    // Create quiz
    @PostMapping("/quiz")
    public String createQuiz(@ModelAttribute Quiz quiz,
            @RequestParam(value = "quizCategory.id", required = false) Long quizCategoryId) {
        if (quizCategoryId != null) {
            QuizCategory category = quizCategoryService.findQuizCategoryById(quizCategoryId);
            quiz.setQuizCategory(category);
        }
        quizService.saveQuiz(quiz);
        return "redirect:/quiz";
    }

    // Get quiz edit view
    @GetMapping("/quiz/edit/{id}")
    public String showEditQuizForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        model.addAttribute("quiz", quiz);
        model.addAttribute("categories", quizCategoryService.findAllQuizCategories());
        return "editquiz"; // Render a view for editing a quiz
    }

    // Update quiz by id
    @PostMapping("/quiz/update/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz,
            @RequestParam(value = "quizCategory.id", required = false) Long quizCategoryId) {
        if (quizCategoryId != null) {
            QuizCategory category = quizCategoryService.findQuizCategoryById(quizCategoryId);
            quiz.setQuizCategory(category);
        } else {
            quiz.setQuizCategory(null);
        }
        quizService.updateQuiz(id, quiz);
        return "redirect:/quiz";
    }

    // Delete quiz by id
    @PostMapping("/quiz/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz";
    }
}