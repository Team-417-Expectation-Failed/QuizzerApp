package fi.haagahelia.quizzerapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Submission {
    
    @Id
    @GeneratedValue
    private Long id;
    private boolean correct;

    @ManyToOne(optional = false)
    @JoinColumn(name = "answerOption_id")
    @JsonIgnore
    private AnswerOption answerOption;

    public Submission(){

    }

    public Submission(Long id, boolean correct){
        this.id = id;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setAnswerOption(AnswerOption answerOption) {
        this.answerOption = answerOption;
    }
}
