package fi.haagahelia.quizzerapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.haagahelia.quizzerapp.domain.QuizCategory;

@Repository
public interface QuizCategoryRepository extends JpaRepository<QuizCategory, Long> {

}
