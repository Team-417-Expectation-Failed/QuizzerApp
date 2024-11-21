package fi.haagahelia.quizzerapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.haagahelia.quizzerapp.domain.Quiz;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByPublished(boolean published);
    List<Quiz> findByQuizCategoryId(Long categoryId);
}
