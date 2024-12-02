package fi.haagahelia.quizzerapp.repositories;

import fi.haagahelia.quizzerapp.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

}
