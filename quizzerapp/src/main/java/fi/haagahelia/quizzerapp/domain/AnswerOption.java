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
    private String answerOptionBody;
    private boolean correct;
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    public AnswerOption() {
    }

    public AnswerOption(String answerOptionBody, boolean correct, Question question) {
        this.answerOptionBody = answerOptionBody;
        this.correct = correct;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
