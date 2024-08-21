package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // requestDto -> Entity
        Schedule entity = new Schedule(requestDto);

        // DB 저장, Entity -> responseDto
        return ScheduleResponseDto.of(scheduleRepository.save(entity));
        
        /* [순서 이해를 위한 코드]
        // requestDto -> Entity
        Schedule entity = new Schedule(requestDto);
        // DB 저장
        Schedule returnEntity = scheduleRepository.save(entity);
        // Entity -> responseDto
        return ScheduleResponseDto.of(returnEntity);
        */
    }

    // 선택한 일정 단건 조회
    public ScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule entity = scheduleRepository.findById(scheduleId).orElseThrow(() -> new RuntimeException("Schedule not found"));
        return ScheduleResponseDto.of(entity);
    }

    public List<ScheduleResponseDto> getSchedules(String date, String manager) {
        List<Schedule> entities = scheduleRepository.search(date, manager);
        return entities.stream().map(ScheduleResponseDto::of).collect(Collectors.toList());
    }
}
