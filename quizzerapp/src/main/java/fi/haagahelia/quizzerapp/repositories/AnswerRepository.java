package fi.haagahelia.quizzerapp.repositories;

import fi.haagahelia.quizzerapp.domain.Answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuizId(Long quizId);
}
