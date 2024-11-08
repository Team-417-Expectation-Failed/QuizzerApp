package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{quizId}")
    public String viewQuizQuestions(@PathVariable("quizId") long quizId, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("questions", questionService.findQuestionsByQuizId(quizId));
        return "viewquizquestions";
    }

    @GetMapping("/question/add/{quizId}")
    public String showAddQuestionForm(@PathVariable Long quizId, Model model) {
        model.addAttribute("quiz", questionService.findQuizById(quizId));
        model.addAttribute("question", new Question());
        return "addquestion";
    }

    @PostMapping("/question/add/{quizId}")
    public String saveQuestion(@PathVariable Long quizId, @ModelAttribute Question question) {
        questionService.addQuestionToQuiz(quizId, question);
        return "redirect:/question/" + quizId;
    }

    @GetMapping("/question/edit/{id}")
    public String showEditQuestionForm(@PathVariable Long id, Model model) {
        Question question = questionService.findQuestionById(id);
        model.addAttribute("question", question);
        return "editquestion";
    }

    @PostMapping("/question/edit/{id}")
    public String updateQuestion(@PathVariable Long id, @ModelAttribute Question question) {
        questionService.updateQuestion(id, question);
        return "redirect:/question/" + question.getQuiz().getId();
    }

    @PostMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, @RequestParam Long quizId) {
        questionService.deleteQuestion(id);
        return "redirect:/question/" + quizId;
    }
}