package fi.haagahelia.quizzerapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String nickname;

    @Min(1)
    @Max(5)
    private int rating;

    @Size(max = 500)
    private String reviewText;

    @NotNull
    private Long quizId;

    // Default constructor
    public ReviewDTO() {
    }

    // Constructor with parameters
    public ReviewDTO(Long id, String nickname, int rating, String reviewText, Long quizId) {
        this.id = id;
        this.nickname = nickname;
        this.rating = rating;
        this.reviewText = reviewText;
        this.quizId = quizId;
    }

    // Getters and setters
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}