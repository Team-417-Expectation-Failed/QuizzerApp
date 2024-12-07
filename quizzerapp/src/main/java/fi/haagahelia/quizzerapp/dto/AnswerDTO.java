package fi.haagahelia.quizzerapp.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerDTO {
    @NotNull
    private Long answerOptionId;

    public AnswerDTO() {}

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