package fi.haagahelia.quizzerapp.repositories;

import fi.haagahelia.quizzerapp.domain.Answer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a JOIN a.answerOption ao JOIN ao.question q JOIN q.quiz qz WHERE qz.id = :quizId")
    List<Answer> findByQuizId(@Param("quizId") Long quizId);

    @Query("SELECT q.id, "
            + "SUM(CASE WHEN a.isCorrect THEN 1 ELSE 0 END) AS correctAnswers, "
            + "SUM(CASE WHEN a.isCorrect = false THEN 1 ELSE 0 END) AS wrongAnswers "
            + "FROM Answer a JOIN a.answerOption ao "
            + "JOIN ao.question q "
            + "WHERE q.quiz.id = :quizId "
            + "GROUP BY q.id")
    List<Object[]> findAnswerStatsByQuizId(@Param("quizId") Long quizId);
}
