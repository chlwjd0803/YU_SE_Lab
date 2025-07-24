package com.example.geminitodo.dto;

import com.example.geminitodo.domain.Category;
import com.example.geminitodo.domain.WebUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private String name;

    public Category toEntity(WebUser webUser) {
        return Category.builder()
                .name(name)
                .webUser(webUser)
                .build();
    }
}
