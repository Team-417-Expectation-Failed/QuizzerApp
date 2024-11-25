package fi.haagahelia.quizzerapp.dto;

import java.time.LocalDate;

public class QuizDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate createdDate;
    private boolean published;
    private String quizCategoryName;

    public QuizDTO() {
    }

    public QuizDTO(Long id, String name, String description, LocalDate createdDate, boolean published, String quizCategoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.published = published;
        this.quizCategoryName = quizCategoryName;
    }

    // Getters and setters  
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

    public String getQuizCategoryName() {
        return quizCategoryName;
    }

    public void setQuizCategory(String quizCategoryName) {
        this.quizCategoryName = quizCategoryName;
    }

    
}
