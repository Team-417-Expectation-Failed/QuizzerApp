package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String questionBody;
    @ManyToOne(optional = false)
    private Quiz belongsToQuiz;

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
