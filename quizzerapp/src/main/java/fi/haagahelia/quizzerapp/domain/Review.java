package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private Integer rating;
    private String reviewText;

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

    public Review(Long id, String nickname, Integer rating, String reviewText) {
        this.id = id;
        this.nickname = nickname;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
