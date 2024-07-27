package com.example.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne()
    @JsonIgnore
    private User user;

    public Long getId() {
        return id;
    }

    public Post() {
    }

    public Post(String description, User user, Long id) {
        this.description = description;
        this.user = user;
        this.id = id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }
}
