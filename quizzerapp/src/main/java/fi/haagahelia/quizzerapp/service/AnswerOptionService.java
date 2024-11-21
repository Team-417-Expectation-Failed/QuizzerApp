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

    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID:" + questionId));
    }

    public void addAnswerOptionToQuestion(Long questionId, AnswerOption answerOption) {
        Question question = findQuestionById(questionId);
        answerOption.setQuestion(question);
        question.getAnswerOptions().add(answerOption);
        answerOptionRepository.save(answerOption);
    }

    public AnswerOption findAnswerOptionById(Long id) {
        return answerOptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid answer option ID:" + id));
    }

    public void updateAnswerOption(Long id, AnswerOption updatedAnswerOption) {
        AnswerOption existingAnswerOption = findAnswerOptionById(id);
        existingAnswerOption.setAnswerOptionBody(updatedAnswerOption.getAnswerOptionBody());
        existingAnswerOption.setCorrect(updatedAnswerOption.isCorrect());
        answerOptionRepository.save(existingAnswerOption);
    }

    public void deleteAnswerOption(Long id) {
        answerOptionRepository.deleteById(id);
    }

    public List<AnswerOption> findAllAnswerOptionsByQuestionId(Long questionId) {
        Question question = findQuestionById(questionId);
        return question.getAnswerOptions();
    }
}