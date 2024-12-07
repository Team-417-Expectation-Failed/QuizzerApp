package fi.haagahelia.quizzerapp.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.repositories.AnswerOptionRepository;
import fi.haagahelia.quizzerapp.repositories.QuestionRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;
import fi.haagahelia.quizzerapp.domain.AnswerOption;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Quiz> findQuizzesByCategoryId(Long categoryId) {
        return quizRepository.findByQuizCategoryId(categoryId);
    }

    public List<Quiz> findAllPublishedQuizzes() {
        List<Quiz> quizzes = quizRepository.findByPublished(true);
        quizzes.forEach(quiz -> {
            if (quiz.getQuizCategory() == null) {
                quiz.setQuizCategory(null);
            }
        });
        return quizzes;
    }

    public Quiz findPublishedQuizById(Long quizId) {
        Quiz quiz = findAllPublishedQuizzes().stream()
                .filter(q -> q.getId().equals(quizId))
                .findFirst()
                .orElse(null);
        return quiz;
    }

    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    public void saveQuiz(Quiz quiz) {
        // Set the quiz property on each question
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
        }
        quizRepository.save(quiz);
    }

    public void updateQuiz(Long id, Quiz updatedQuiz) {
        Quiz existingQuiz = findQuizById(id);
        existingQuiz.setName(updatedQuiz.getName());
        existingQuiz.setDescription(updatedQuiz.getDescription());
        existingQuiz.setPublished(updatedQuiz.isPublished());
        // Update the quiz category if provided
        if (updatedQuiz.getQuizCategory() != null) {
            existingQuiz.setQuizCategory(updatedQuiz.getQuizCategory());
        } else {
            existingQuiz.setQuizCategory(null);
        }
        // Preserve existing questions and answer options
        existingQuiz.setQuestions(existingQuiz.getQuestions());

        // Save the updated quiz to the repository
        quizRepository.save(existingQuiz);
    }

    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }

    public Quiz createEmptyQuiz() {
        Quiz quiz = new Quiz();
        quiz.setQuestions(new ArrayList<>());
        quiz.getQuestions().add(new Question());
        return quiz;
    }

    public void addQuestionToQuiz(Long quizId, Question question) {
        Quiz quiz = findQuizById(quizId);
        question.setQuiz(quiz);
        quiz.getQuestions().add(question);
        questionRepository.save(question);
    }

    public void addAnswerOptionToQuestion(Long questionId, AnswerOption answerOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID:" + questionId));
        answerOption.setQuestion(question);
        question.getAnswerOptions().add(answerOption);
        answerOptionRepository.save(answerOption);
    }

    public boolean isQuizPublished(Long quizId) {
        Quiz quiz = findQuizById(quizId);
        return quiz.isPublished();
    }
}