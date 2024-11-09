package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.service.QuestionService;

@Controller
@RequestMapping("/quiz/{quizId}/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Get all questions by quiz id
    @GetMapping
    public String getAllQuestions(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("questions", questionService.findQuestionsByQuizId(quizId));
        return "questionlist"; // View for all questions in the quiz
    }

    // Get question by id
    @GetMapping("/{questionId}")
    public String getQuestionById(@PathVariable Long quizId, @PathVariable Long questionId, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("question", questionService.findQuestionById(questionId));
        return "questionview"; // View for a specific question
    }

    // Show add question form view
    @GetMapping("/add")
    public String showAddQuestionForm(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("question", new Question());
        return "addquestion"; // View for adding a question
    }

    // Save new question to quiz id
    @PostMapping("/add")
    public String saveNewQuestion(@PathVariable Long quizId, @ModelAttribute Question question) {
        questionService.addQuestionToQuiz(quizId, question);
        return "redirect:/quiz/" + quizId + "/questions";
    }

    // Show edit question form view
    @GetMapping("/edit/{id}")
    public String showEditQuestionForm(@PathVariable Long quizId, @PathVariable Long id, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("question", questionService.findQuestionById(id));
        return "editquestion"; // View for editing a question
    }

    // Update question by id
    @PostMapping("/edit/{id}")
    public String updateQuestion(@PathVariable Long quizId, @PathVariable Long id, @ModelAttribute Question question) {
        questionService.updateQuestion(id, question);
        return "redirect:/quiz/" + quizId + "/questions";
    }

    // Delete question by id
    @PostMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Long quizId, @PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/quiz/" + quizId + "/questions";
    }
}