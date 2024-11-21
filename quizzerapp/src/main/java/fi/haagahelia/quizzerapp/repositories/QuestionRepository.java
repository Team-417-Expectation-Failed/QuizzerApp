package fi.haagahelia.quizzerapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.haagahelia.quizzerapp.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
