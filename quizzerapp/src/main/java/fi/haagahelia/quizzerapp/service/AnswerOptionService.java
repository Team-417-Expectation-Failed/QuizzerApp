package fi.haagahelia.quizzerapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;

@Service
public class AnswerOptionService {

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Method to find a question by its ID
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID:" + questionId));
    }

    // Method to add an answer option to a specific question
    public void addAnswerOptionToQuestion(Long questionId, AnswerOption answerOption) {
        Question question = findQuestionById(questionId);  // Find the question by ID
        answerOption.setQuestion(question);  // Associate the answer option with the question
        question.getAnswerOptions().add(answerOption);  // Add the answer option to the question's list of answer options
        answerOptionRepository.save(answerOption);  // Save the new answer option to the database
    }

    // Method to find an answer option by its ID
    public AnswerOption findAnswerOptionById(Long id) {
        return answerOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid answer option ID:" + id));
    }

    // Method to update an existing answer option
    public void updateAnswerOption(Long id, AnswerOption updatedAnswerOption) {
        AnswerOption existingAnswerOption = findAnswerOptionById(id);  // Find the existing answer option
        existingAnswerOption.setAnswerOptionBody(updatedAnswerOption.getAnswerOptionBody());  // Update fields
        existingAnswerOption.setCorrect(updatedAnswerOption.isCorrect());
        answerOptionRepository.save(existingAnswerOption);  // Save the updated answer option
    }

    // Method to delete an answer option by its ID
    public void deleteAnswerOption(Long id) {
        answerOptionRepository.deleteById(id);  // Delete the answer option from the repository
    }

    // Method to find all answer options by a specific question ID
    public List<AnswerOption> findAllAnswerOptionsByQuestionId(Long questionId) {
        Question question = findQuestionById(questionId);  // Find the question by its ID
        return question.getAnswerOptions();  // Return the list of answer options associated with the question
    }
}
