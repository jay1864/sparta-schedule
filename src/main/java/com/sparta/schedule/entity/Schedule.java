package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 생성
@Getter
public class Schedule {
    private long id;  // 일정 id
    private String schedule;  // 일정 내용
    private String manager;   // 관리자
    private String pw;    // 비밀번호
    private LocalDateTime createdAt;  // 생성 일시
    private LocalDateTime updatedAt;  // 수정 일시

    // requestDto -> Entity
    public Schedule(ScheduleRequestDto requestDto) {
        this.schedule = requestDto.getSchedule();
        this.manager = requestDto.getManager();
        this.pw = requestDto.getPw();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // id값 입력 (DB에서 가져온 값)
    public void changeId(long id) {
        this.id = id;
    }

}
