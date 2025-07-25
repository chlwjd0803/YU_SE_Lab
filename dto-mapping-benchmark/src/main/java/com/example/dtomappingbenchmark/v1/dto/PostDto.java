package com.example.dtomappingbenchmark.v1.dto;

import com.example.dtomappingbenchmark.v1.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long userId;

    public Post toEntity(){
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .user(null)
                .build();
    }
}
