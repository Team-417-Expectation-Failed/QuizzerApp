package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.service.AnswerOptionService;

@Controller
public class AnswerOptionController {

    @Autowired
    private AnswerOptionService answerOptionService;

    @GetMapping("/question/{questionId}/addAnswerOption")
    public String showAddAnswerOptionForm(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", answerOptionService.findQuestionById(questionId));
        model.addAttribute("answerOption", new AnswerOption());
        return "addansweroption";
    }

    @PostMapping("/question/{questionId}/addAnswerOption")
    public String saveAnswerOption(@PathVariable Long questionId, @ModelAttribute AnswerOption answerOption) {
        answerOptionService.addAnswerOptionToQuestion(questionId, answerOption);
        return "redirect:/question/" + questionId + "/viewAnswerOptions";
    }

    @GetMapping("/answerOption/edit/{id}")
    public String showEditAnswerOptionForm(@PathVariable Long id, Model model) {
        model.addAttribute("answerOption", answerOptionService.findAnswerOptionById(id));
        return "editansweroption";
    }

    @PostMapping("/answerOption/edit/{id}")
    public String updateAnswerOption(@PathVariable Long id, @ModelAttribute AnswerOption answerOption) {
        AnswerOption existingAnswerOption = answerOptionService.findAnswerOptionById(id);
        answerOption.setQuestion(existingAnswerOption.getQuestion()); // Ensure the Question object is set
        answerOptionService.updateAnswerOption(id, answerOption);
        return "redirect:/question/" + answerOption.getQuestion().getId() + "/viewAnswerOptions";
    }

    @PostMapping("/answerOption/delete/{id}")
    public String deleteAnswerOption(@PathVariable Long id, @RequestParam Long questionId) {
        answerOptionService.deleteAnswerOption(id);
        return "redirect:/question/" + questionId + "/viewAnswerOptions";
    }

    @GetMapping("/question/{questionId}/viewAnswerOptions")
    public String viewAnswerOptions(@PathVariable Long questionId, Model model) {
        model.addAttribute("question", answerOptionService.findQuestionById(questionId));
        model.addAttribute("answerOptions", answerOptionService.findQuestionById(questionId).getAnswerOptions());
        return "viewansweroptions";
    }
}