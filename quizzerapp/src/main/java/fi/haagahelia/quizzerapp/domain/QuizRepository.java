package fi.haagahelia.quizzerapp.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAll();
}

// public interface QuizRepository extends CrudRepository<Quiz, Long> {

// List<Quiz> findAll();

// }
