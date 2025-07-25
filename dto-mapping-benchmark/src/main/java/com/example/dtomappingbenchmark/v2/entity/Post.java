package com.example.dtomappingbenchmark.v2.entity;

import com.example.dtomappingbenchmark.v1.dto.PostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    public PostDto toDto(){
        return PostDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .userId(user.getId())
                .build();
    }
}
