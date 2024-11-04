package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String questionBody;
    @ManyToOne(optional = false)
    private Quiz belongsToQuiz;

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
    public Quiz getBelongsToQuiz() {
        return belongsToQuiz;
    }
    public void setBelongsToQuiz(Quiz belongsToQuiz) {
        this.belongsToQuiz = belongsToQuiz;
    }
}
