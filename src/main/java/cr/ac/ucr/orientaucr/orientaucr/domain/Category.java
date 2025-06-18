package cr.ac.ucr.orientaucr.orientaucr.domain;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id", length = 36)
    private String categoryId;

    @Column(name = "category_name", unique = true, nullable = false, length = 100)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<SimulationQuestion> questions = new ArrayList<>();

    public Category() {
        this.categoryId = UUID.randomUUID().toString();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<SimulationQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SimulationQuestion> questions) {
        this.questions = questions;
    }
    
    
}