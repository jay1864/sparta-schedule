package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable long scheduleId) {
        ScheduleResponseDto responseDto = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/api/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(@RequestParam(required = false) String date, @RequestParam(required = false) String manager) {
        List<ScheduleResponseDto> responseDtos = scheduleService.getSchedules(date, manager);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(scheduleId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/api/schedules/{scheduleId}")
    public void deleteSchedule(@PathVariable long scheduleId, @RequestBody ScheduleRequestDto requestDto) {
        scheduleService.deleteSchedule(scheduleId, requestDto);
    }

}
