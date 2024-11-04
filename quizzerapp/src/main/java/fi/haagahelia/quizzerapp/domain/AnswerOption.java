package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answerOption;
    private boolean correct;
    @ManyToOne(optional = false)
    private Question belongsToQuestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
