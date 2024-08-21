package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody = 해당 클래스의 모든 메서드에 @ResponseBody 애너테이션 추가
@RequiredArgsConstructor // final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성 (의존성 주입)
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/api/schedules")
    // @RequestBody : request로 넘어온 json 데이터를 ScheduleRequestDto 객체로 변환
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.ok(responseDto);  // ok(200 code)와 함께 ScheduleRequestDto 객체 return
    }

    @GetMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long scheduleId) {
        ScheduleResponseDto responseDto = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.ok(responseDto);
    }

}
