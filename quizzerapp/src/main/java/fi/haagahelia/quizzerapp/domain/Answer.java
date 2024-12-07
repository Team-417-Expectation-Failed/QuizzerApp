package fi.haagahelia.quizzerapp.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // prefer @NotNull instead of @Column(nullable = false) for validation
    @NotNull
    private boolean isCorrect;

    // Relate Answer only to AnswerOption
    @ManyToOne
    @JoinColumn(name = "answer_option_id", nullable = false)
    @JsonIgnore
    private AnswerOption answerOption;

    public Answer() {
    }

    public Answer(AnswerOption answerOption, boolean isCorrect) {
        this.answerOption = answerOption;
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerOption getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(AnswerOption answerOption) {
        this.answerOption = answerOption;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    // toString method
    @Override
    public String toString() {
    return "Answer{id=" + id + ", isCorrect=" + isCorrect + "}";
    }

    // equals and hashCode methods to compare Answer objects
    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Answer answer = (Answer) o;
    return isCorrect == answer.isCorrect && id.equals(answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isCorrect);
    }
}
