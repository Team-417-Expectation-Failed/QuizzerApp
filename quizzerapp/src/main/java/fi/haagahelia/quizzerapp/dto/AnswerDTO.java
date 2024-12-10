package fi.haagahelia.quizzerapp.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerDTO {
    @NotNull(message = "Answer option ID is required")
    private Long answerOptionId;

    public AnswerDTO() {
    }

    public AnswerDTO(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public Long getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }
}