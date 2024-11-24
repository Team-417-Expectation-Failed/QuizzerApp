package fi.haagahelia.quizzerapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AnswerOption {

    @Id
    @GeneratedValue
    private Long id; // Unique identifier for AnswerOption

    private String answerOptionBody; // Text of the answer option
    private boolean correct; // Indicates if the answer option is correct

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    @JsonIgnore // Prevent serializing the associated Question object
    private Question question; // Reference to the associated Question

    // Default constructor
    public AnswerOption() {
    }

    // Constructor with parameters
    public AnswerOption(String answerOptionBody, boolean correct, Question question) {
        this.answerOptionBody = answerOptionBody;
        this.correct = correct;
        this.question = question;
    }

    // Getter and Setter methods
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

    // This method can be kept, though it's essentially the same as getAnswerOptionBody()
    public String getOptionText() {
        return answerOptionBody;
    }

    // Override toString() to provide meaningful information about AnswerOption
    @Override
    public String toString() {
        return "AnswerOption{" +
                "id=" + id +
                ", answerOptionBody='" + answerOptionBody + '\'' +
                ", correct=" + correct +
                '}';
    }
}
