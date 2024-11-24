package fi.haagahelia.quizzerapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.service.AnswerOptionService;

@Controller
@RequestMapping("/quiz/{quizId}/questions/{questionId}/answers")
public class AnswerOptionController {

    @Autowired
    private AnswerOptionService answerOptionService;

    // Get all answer options by question id
    @GetMapping
    public String getAllAnswerOptions(@PathVariable Long quizId, @PathVariable Long questionId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("question", answerOptionService.findQuestionById(questionId));
        model.addAttribute("answerOptions", answerOptionService.findAllAnswerOptionsByQuestionId(questionId));
        return "answeroptionlist"; // View for all answer options for a question
    }

    // Get answer option by id
    @GetMapping("/{answerOptionId}")
    public String getAnswerOptionById(@PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long answerOptionId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("answerOption", answerOptionService.findAnswerOptionById(answerOptionId));
        return "answeroptionview"; // View for a specific answer option
    }

    // Show add question form view
    @GetMapping("/add")
    public String showAddQuestionForm(@PathVariable Long quizId, @PathVariable Long questionId, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("newAnswerOption", new AnswerOption());
        return "addansweroption"; // View for adding an answer option
    }

    // Save new answer option to question
    @PostMapping("/add")
    public String saveNewAnswerOption(@PathVariable Long quizId, @PathVariable Long questionId, @ModelAttribute AnswerOption answerOption) {
        answerOptionService.addAnswerOptionToQuestion(questionId, answerOption); // Use AnswerOptionService to add the option to the question
        return "redirect:/quiz/" + quizId + "/questions/" + questionId + "/answers";
    }

    // Show edit answer option form view
    @GetMapping("/edit/{id}")
    public String showEditAnswerOptionForm(@PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long id, Model model) {
        model.addAttribute("quizId", quizId);
        model.addAttribute("questionId", questionId);
        AnswerOption answerOption = answerOptionService.findAnswerOptionById(id);
        model.addAttribute("answerOption", answerOption);
        return "editansweroption"; // View for editing an answer option
    }

    // Update answer option
    @PostMapping("/edit/{id}")
    public String updateAnswerOption(@PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long id, @ModelAttribute AnswerOption answerOption) {
        answerOptionService.updateAnswerOption(id, answerOption);
        return "redirect:/quiz/" + quizId + "/questions/" + questionId + "/answers";
    }

    // Delete answer option
    @PostMapping("/delete/{id}")
    public String deleteAnswerOption(@PathVariable Long quizId, @PathVariable Long questionId, @PathVariable Long id) {
        answerOptionService.deleteAnswerOption(id);
        return "redirect:/quiz/" + quizId + "/questions/" + questionId + "/answers";
    }
}