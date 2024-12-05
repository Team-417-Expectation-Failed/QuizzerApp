package fi.haagahelia.quizzerapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.haagahelia.quizzerapp.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
}
