package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter  // 객체의 필드에 데이터를 넣어주기 위해 set or get 메서드 또는 오버로딩된 생성자가 필요
public class ScheduleResponseDto {
    // dto는 필드변수에 final!!!
    private final long id;  // 일정 id
    private final String schedule;  // 일정 내용
    private final String manager;   // 관리자
    private final LocalDateTime createdAt;  // 생성 일시
    private final LocalDateTime updatedAt;  // 수정 일시

    // Entity -> responseDto (변환 후 반환)
    // Entity -> DTO에서 'of' naming : https://docs.oracle.com/javase/tutorial/datetime/overview/naming.html
    public static ScheduleResponseDto of(Schedule entity){
        return new ScheduleResponseDto(entity.getId(), entity.getSchedule(), entity.getManager(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
