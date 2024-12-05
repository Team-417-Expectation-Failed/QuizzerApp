package fi.haagahelia.quizzerapp.domain;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @CreationTimestamp
    private LocalDate reviewDate;

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

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

    public Review() {
    }

    public Review(String nickname, Integer rating, String reviewText, Quiz quiz) {
        this.nickname = nickname;
        this.rating = rating;
        this.reviewText = reviewText;
        this.quiz = quiz;
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
