package com.example.geminitodo.dto;

import com.example.geminitodo.domain.Category;
import com.example.geminitodo.domain.Todo;
import com.example.geminitodo.domain.WebUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TodoDto {
    private String title;
    private String status;
    private LocalDate deadline;
    private boolean favorite;
    private Long categoryId;

    public Todo toEntity(WebUser webUser, Category category) {
        return Todo.builder()
                .title(title)
                .status(status)
                .deadline(deadline)
                .favorite(favorite)
                .webUser(webUser)
                .category(category)
                .build();
    }
}
