package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter  // 객체의 필드에 데이터를 넣어주기 위해 set or get 메서드 또는 오버로딩된 생성자가 필요
public class ScheduleRequestDto {
    // dto는 필드변수에 final!!!
    private final String schedule;  // 일정 내용
    private final String manager;   // 관리자
    private final String pw;    // 비밀번호
}
