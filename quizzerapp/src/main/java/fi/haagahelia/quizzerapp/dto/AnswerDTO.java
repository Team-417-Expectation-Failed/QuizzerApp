package fi.haagahelia.quizzerapp.dto;

import jakarta.validation.constraints.NotNull;

public class AnswerDTO {

    @NotNull
    private Long questionId;
    @NotNull
    private Long answerOptionId;
    @NotNull
    private Long quizId;
    private Boolean correct;

    public AnswerDTO() {
    }

    public AnswerDTO(Long questionId, Long answerOptionId, Long quizId) {
        this.questionId = questionId;
        this.answerOptionId = answerOptionId;
        this.quizId = quizId;
        this.correct = false;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerOptionId() {
        return answerOptionId;
    }

    public void setAnswerOptionId(Long answerOptionId) {
        this.answerOptionId = answerOptionId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
                "questionId=" + questionId +
                ", answerOptionId=" + answerOptionId +
                ", quizId=" + quizId +
                ", correct=" + correct +
                '}';
    }
}
