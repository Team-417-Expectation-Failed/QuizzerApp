package fi.haagahelia.quizzerapp.repositories;

import fi.haagahelia.quizzerapp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // This method retrieves all questions from a specific survey
    List<Question> findByQuizId(Long quizId);
}
