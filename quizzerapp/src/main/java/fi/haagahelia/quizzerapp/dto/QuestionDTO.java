package fi.haagahelia.quizzerapp.dto;

import java.util.List;
import fi.haagahelia.quizzerapp.domain.DifficultyLevel;

public class QuestionDTO {
    private Long id;
    private String questionBody;
    private List<AnswerOptionDTO> answerOptions; // Changed the answer options to also include information about the correct answer
    private DifficultyLevel difficultyLevel;

    public QuestionDTO() {}

    public QuestionDTO(Long id, String questionBody, DifficultyLevel difficultyLevel, List<AnswerOptionDTO> answerOptions) {
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

    public List<AnswerOptionDTO> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOptionDTO> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
