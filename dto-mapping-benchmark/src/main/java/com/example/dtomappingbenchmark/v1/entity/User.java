package com.example.dtomappingbenchmark.v1.entity;

import com.example.dtomappingbenchmark.v1.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String email;

    // User가 Post의 생명주기까지 통제하겠다. 이거 유용하게 사용할 수 있어보임
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public UserDto toDto(){
        return UserDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .postList(posts.stream()
                        .map(Post::toDto)
                        .toList())
                .build();
    }
}
