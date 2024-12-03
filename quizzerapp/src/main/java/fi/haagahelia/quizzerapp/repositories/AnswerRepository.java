package fi.haagahelia.quizzerapp.repositories;

import fi.haagahelia.quizzerapp.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
