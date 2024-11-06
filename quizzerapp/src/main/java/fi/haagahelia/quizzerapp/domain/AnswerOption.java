package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AnswerOption {
    @Id
    @GeneratedValue
    private Long id;
    private String answerOption;
    private boolean correct;
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question belongsToQuestion;

    public AnswerOption() {
    }

    public AnswerOption(String answerOption, boolean correct, Question belongsToQuestion) {
        this.answerOption = answerOption;
        this.correct = correct;
        this.belongsToQuestion = belongsToQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getBelongsToQuestion() {
        return belongsToQuestion;
    }

    public void setBelongsToQuestion(Question belongsToQuestion) {
        this.belongsToQuestion = belongsToQuestion;
    }
}
