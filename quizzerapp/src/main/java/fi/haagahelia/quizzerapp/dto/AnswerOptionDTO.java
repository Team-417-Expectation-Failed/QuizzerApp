package fi.haagahelia.quizzerapp.dto;

public class AnswerOptionDTO {
    private String answerOptionBody; // The string of the answer-option
    private boolean correct; // the boolean if the answer option is correct

    public AnswerOptionDTO() {}

    // Constructor
    public AnswerOptionDTO(String answerOptionBody, boolean correct) {
        this.answerOptionBody = answerOptionBody;
        this.correct = correct;
    }

    // Getters and setters
    public String getAnswerOptionBody() {
        return answerOptionBody;
    }

    public void setAnswerOptionBody(String answerOptionBody) {
        this.answerOptionBody = answerOptionBody;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
