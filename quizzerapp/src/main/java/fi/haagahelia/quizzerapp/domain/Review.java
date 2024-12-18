package fi.haagahelia.quizzerapp.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private Integer rating;
    private String reviewText;
    private LocalDate reviewDate;

    // Specifies that each Review must be associated with a Quiz and the association
    // is mandatory (not optional)
    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Review() {
    }

    public Review(String nickname, Integer rating, String reviewText, Quiz quiz, LocalDate reviewDate) {
        this();
        this.nickname = nickname;
        this.rating = rating;
        this.reviewText = reviewText;
        this.quiz = quiz;
        this.reviewDate = reviewDate;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                ", quiz=" + quiz +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
