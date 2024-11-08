package fi.haagahelia.quizzerapp.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzerapp.domain.Question;
import fi.haagahelia.quizzerapp.domain.QuestionRepository;
import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizRepository;
import fi.haagahelia.quizzerapp.domain.AnswerOption;
import fi.haagahelia.quizzerapp.domain.AnswerOptionRepository;

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

    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz ID:" + id));
    }

    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
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
        quizRepository.save(quiz);
    }

    public void addAnswerOptionToQuestion(Long questionId, AnswerOption answerOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID:" + questionId));
        answerOption.setQuestion(question);
        question.getAnswerOptions().add(answerOption);
        answerOptionRepository.save(answerOption);
    }
}