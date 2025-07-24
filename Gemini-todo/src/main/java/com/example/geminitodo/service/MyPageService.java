package com.example.geminitodo.service;

import com.example.geminitodo.dto.MyPageDto;
import com.example.geminitodo.dto.UserRankDto;
import com.example.geminitodo.repository.TodoRepository;
import com.example.geminitodo.repository.WebUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final WebUserRepository webUserRepository;
    private final TodoRepository todoRepository;

    public MyPageDto getMyPageInfo(Long userId) {
        // 1. 현재 사용자의 할 일 진행률 계산
        long totalTasks = todoRepository.findByWebUserId(userId).size();
        long completedTasks = todoRepository.findByWebUserIdAndStatus(userId, "완료").size();
        double myProgress = (totalTasks == 0) ? 0.0 : (double) completedTasks / totalTasks * 100;

        // 2. 모든 사용자의 랭킹 계산
        List<UserRankDto> allUserRanks = webUserRepository.findAll().stream()
                .map(user -> {
                    long userTotalTasks = todoRepository.findByWebUserId(user.getId()).size();
                    long userCompletedTasks = todoRepository.findByWebUserIdAndStatus(user.getId(), "완료").size();
                    double userProgress = (userTotalTasks == 0) ? 0.0 : (double) userCompletedTasks / userTotalTasks * 100;
                    return new UserRankDto(user.getUsername(), userProgress);
                })
                .sorted((u1, u2) -> Double.compare(u2.getProgress(), u1.getProgress())) // 진행률 내림차순 정렬
                .collect(Collectors.toList());

        // 3. 내 랭킹 찾기
        int myRank = -1;
        for (int i = 0; i < allUserRanks.size(); i++) {
            if (allUserRanks.get(i).getUsername().equals(webUserRepository.findById(userId).orElseThrow().getUsername())) {
                myRank = i + 1;
                break;
            }
        }

        // 4. 상위 10명 사용자 가져오기
        List<UserRankDto> top10Users = allUserRanks.stream()
                .limit(10)
                .collect(Collectors.toList());

        return new MyPageDto(myProgress, myRank, top10Users);
    }
}
