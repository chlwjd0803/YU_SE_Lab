package com.example.geminitodo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "favorite")
    private boolean favorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "web_user_id")
    private WebUser webUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Todo(String title, String status, LocalDate deadline, boolean favorite, WebUser webUser, Category category) {
        this.title = title;
        this.status = status;
        this.deadline = deadline;
        this.favorite = favorite;
        this.webUser = webUser;
        this.category = category;
    }
}
