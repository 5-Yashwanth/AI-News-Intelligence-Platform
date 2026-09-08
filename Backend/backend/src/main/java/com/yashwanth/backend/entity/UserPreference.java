package com.yashwanth.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_preferences")
@Data
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private boolean aiMl = true;

    @Column(nullable = false)
    private boolean software = true;

    @Column(nullable = false)
    private boolean indiaNews = true;

    @Column(nullable = false)
    private boolean bigTech = true;

    @Column(nullable = false)
    private boolean worldBusiness = true;

    @Column(nullable = false)
    private boolean sportsMovies = true;
}