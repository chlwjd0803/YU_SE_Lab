package com.example.geminitodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageDto {
    private double progress;
    private int myRank;
    private List<UserRankDto> top10Users;
}
