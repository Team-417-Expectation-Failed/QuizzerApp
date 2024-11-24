package fi.haagahelia.quizzerapp.service;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    // Method to find a quiz by its ID
    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    // Method to find all questions for a specific quiz
    public List<Question> findQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    // Method to find a specific question by its ID
    public Question findQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    // Method to add a question to a quiz
    public void addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = findQuizById(quizId);
        question.setQuiz(quiz); // Associate the question with the quiz
        questionRepository.save(question);
    }

    // Method to update a question by its ID
    public void updateQuestion(Long questionId, Question updatedQuestion) {
        Question existingQuestion = findQuestionById(questionId);
        existingQuestion.setQuestionBody(updatedQuestion.getQuestionBody());
        existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
        // Add any other fields to update
        questionRepository.save(existingQuestion);
    }

    // Method to delete a question by its ID
    public void deleteQuestion(Long questionId) {
        Question question = findQuestionById(questionId);
        questionRepository.delete(question);
    }
}
