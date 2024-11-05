package fi.haagahelia.quizzerapp.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @CreationTimestamp
    private LocalDate createdDate;
    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    //Initiates a list for questions objects belonging to this quiz
    private List<Question> questions = new ArrayList<>();
    

    public Quiz(Long id, String name, String description, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;

    }

    public Quiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    
}
