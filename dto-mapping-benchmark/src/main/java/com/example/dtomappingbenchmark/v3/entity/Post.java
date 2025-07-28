package com.example.dtomappingbenchmark.v3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "PostV3")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String title;
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩을 이용하기 위해
    private User user;
}
