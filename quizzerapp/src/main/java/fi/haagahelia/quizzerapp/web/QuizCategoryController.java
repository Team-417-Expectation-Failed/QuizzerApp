package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.service.QuizCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuizCategoryController {

    @Autowired
    private QuizCategoryService quizCategoryService;

    // Get all quiz categories
    @GetMapping("/quizCategories")
    public String getAllQuizCategories(Model model) {
        model.addAttribute("quizCategories", quizCategoryService.findAllQuizCategories());
        return "quizCategoryList";
    }

    // Get quiz category by id
    @GetMapping("/quizCategories/{quizCategoryId}")
    public String getQuizCategoryById(@PathVariable Long quizCategoryId, Model model) {
        model.addAttribute("quizCategory", quizCategoryService.findQuizCategoryById(quizCategoryId));
        return "quizCategoryView";
    }

    // Adding a quiz category form
    @GetMapping("/quizCategories/add")
    public String showAddQuizCategoryForm(Model model) {
        model.addAttribute("quizCategories", new QuizCategory());
        return "addQuizCategory";
    }

    // Create quiz category
    @PostMapping("/quizCategories")
    public String createQuizCategory(@ModelAttribute QuizCategory quizCategory) {
        quizCategoryService.saveQuizCategory(quizCategory);
        return "redirect:/quizCategories";
    }

}
