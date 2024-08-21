package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    // DB 저장
    public Schedule save(Schedule entity) {
        String sql = "INSERT INTO schedule(schedule, manager, pw, created_at, updated_at) values(?,?,?,?,?)";
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

    // 주어진 id로 DB 조회
    public Optional<Schedule> findById(long scheduleId) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplate.query(sql, scheduleRowMapper(), scheduleId).stream().findFirst();
    }

    // date, manager로 DB 조회
    public List<Schedule> search(String date, String manager) {
        // date, manager 유무에 따라 쿼리가 추가될수도 있기 때문에 String 대신 추가가 가능한 StringBuilder를 사용한다.
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE id>0");
        // 추가된 쿼리의 ?에 들어갈 param을 저장한다.
        List<String> params = new ArrayList<>();

        if(date != null && !date.isEmpty()) {   // date가 있을 경우
            LocalDate searchDate = LocalDate.parse(date);
            sql.append(" AND DATE(updated_at) = ?");
            params.add(searchDate.toString());
        }

        if(manager != null && !manager.isEmpty()) { // manager가 있을 경우
            sql.append(" AND manager = ?");
            params.add(manager);
        }

        sql.append(" ORDER BY updated_at DESC");    // 정렬 쿼리

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    // DB 수정
    public void update(Schedule entity) {
        String sql = "UPDATE schedule SET schedule = ?, manager = ?, updated_at = NOW() WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getSchedule());
        params.add(entity.getManager());
        params.add(String.valueOf(entity.getId())); // id값은 long이므로 형변환

        jdbcTemplate.update(sql, params.toArray());
    }

    // 해당 튜플 DB에서 삭제
    public void delete(long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, scheduleId);
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) ->
                new Schedule(
                        rs.getLong("id"),
                        rs.getString("schedule"),
                        rs.getString("manager"),
                        rs.getString("pw"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
    }

}