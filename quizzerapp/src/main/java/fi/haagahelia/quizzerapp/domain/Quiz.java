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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "quizCategory_id")
    @JsonIgnore
    private QuizCategory quizCategory;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(String name, String description, LocalDate createdDate, boolean published) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.published = published;
    }

    // Constructor for test data
    public Quiz(String name, String description, boolean published, QuizCategory quizCategory) {
        this.name = name;
        this.description = description;
        this.published = published;
        this.quizCategory = quizCategory;
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

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public QuizCategory getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(QuizCategory quizCategory) {
        this.quizCategory = quizCategory;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", published=" + published +
                ", quizCategory=" + (quizCategory != null ? quizCategory.getName() : "null") +
                '}';
    }
}
