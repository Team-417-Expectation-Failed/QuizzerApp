package fi.haagahelia.quizzerapp.dto;

import java.util.List;
import fi.haagahelia.quizzerapp.domain.DifficultyLevel;

public class QuestionDTO {
    private Long id;
    private String questionBody;
    private List<String> answerOptions;
    private DifficultyLevel difficultyLevel;

    // Oletuskonstruktori (ilman parametreja)
    public QuestionDTO() {
        // Ei tarvitse tehdä mitään tässä, jos ei ole erityisiä alustuslogiikoita
    }
    // Muokattu konstruktori, joka ottaa DifficultyLevel ja List<String>
    public QuestionDTO(Long id, String questionBody, DifficultyLevel difficultyLevel, List<String> answerOptions) {
        this.id = id;
        this.questionBody = questionBody;
        this.difficultyLevel = difficultyLevel;
        this.answerOptions = answerOptions;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
