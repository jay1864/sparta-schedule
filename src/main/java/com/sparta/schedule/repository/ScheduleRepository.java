package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    // DB 저장
    public Schedule save(Schedule entity) {
        String sql = "INSERT INTO schedule(schedule, manager, pw, created_at, updated_at) values(?,?,?,?,?)";   // query

        KeyHolder keyHolder = new GeneratedKeyHolder(); // insert 후 자동 생성된 id를 반환받기 위해 KeyHolder의 GeneratedKeyHolder 객체 사용

//        DB 컬럼명과 entity 객체 속성 이름이 달라서(작성일, 수정일) 에러 발생! 나중에 db 수정 후 사용해보는걸로.
//        SqlParameterSource params = new BeanPropertySqlParameterSource(entity); //entity 객체의 속성을 SQL 쿼리의 매개변수로 자동으로 매핑(매개변수 이름으로 바인딩)
//        jdbcTemplate.update(sql, params, keyHolder);

        jdbcTemplate.update(connection -> {
            PreparedStatement params = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            params.setObject(1, entity.getSchedule());
            params.setObject(2, entity.getManager());
            params.setObject(3, entity.getPw());
            params.setObject(4, entity.getCreatedAt());
            params.setObject(5, entity.getUpdatedAt());
            return params;
        }, keyHolder);


        // DB Insert 후 받아온 기본키 확인
        long id = Objects.requireNonNull(keyHolder.getKey()).longValue(); // requireNonNull : 입력된 값이 null 이면 NullPointerException 발생, 아니면 그대로 반환
        entity.changeId(id);

        return entity;
    }
}
