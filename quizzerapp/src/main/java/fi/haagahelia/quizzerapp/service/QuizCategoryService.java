package fi.haagahelia.quizzerapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.quizzerapp.domain.Quiz;
import fi.haagahelia.quizzerapp.domain.QuizCategory;
import fi.haagahelia.quizzerapp.repositories.QuizCategoryRepository;
import fi.haagahelia.quizzerapp.repositories.QuizRepository;

@Service
public class QuizCategoryService {

    @Autowired
    private QuizCategoryRepository quizCategoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    public List<QuizCategory> findAllQuizCategories() {
        return quizCategoryRepository.findAll();
    }

    public QuizCategory findQuizCategoryById(Long quizCategoryId) {
        return quizCategoryRepository.findById(quizCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid quiz category ID:" + quizCategoryId));
    }

    public void addQuizToQuizCategory(Long QuizCategoryId, Quiz quiz) {
        QuizCategory quizCategory = findQuizCategoryById(QuizCategoryId);
        quiz.setQuizCategory(quizCategory);
        quizRepository.save(quiz);
    }

    public void saveQuizCategory(QuizCategory quizCategory) {
        for (Quiz quiz : quizCategory.getQuizzes()) {
            quiz.setQuizCategory(quizCategory);
        }
        quizCategoryRepository.save(quizCategory);
    }

    public void deleteQuizCategory(Long quizCategoryId) {

        quizCategoryRepository.findById(quizCategoryId).ifPresent(quizCategory -> {
          
            for (Quiz quiz : quizCategory.getQuizzes()) {
                quiz.setQuizCategory(null);
                quizRepository.save(quiz);
            }
           
            quizCategoryRepository.deleteById(quizCategoryId);
        });
    }
}
