package fi.haagahelia.quizzerapp.dto;

public class QuestionStatsDTO {
    private Long questionId;
    private String questionText;
    private int correctAnswers;
    private int wrongAnswers;

    public QuestionStatsDTO(Long questionId, String questionText, int correctAnswers, int wrongAnswers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
    }

    // Getters and setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    @Override
    public String toString() {
        return "QuestionStatsDTO [correctAnswers=" + correctAnswers + ", questionId=" + questionId + ", questionText="
                + questionText + ", wrongAnswers=" + wrongAnswers + "]";
    }
}
