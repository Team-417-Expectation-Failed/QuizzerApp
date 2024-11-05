package fi.haagahelia.quizzerapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String questionBody;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id")
    //Quiz where this question belongs to
    private Quiz quiz;

    public  Question() {

    }

    public Question(String questionBody) {
        this.questionBody = questionBody;
    }

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

}
