package com.example.plan.repository;

import com.example.plan.dto.PlanResponseDto;
import com.example.plan.entity.Plan;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplatePlanRepository implements PlanRepository{
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplatePlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PlanResponseDto savePlan(Plan plan) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("plan").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", plan.getName());
        parameters.put("pw", plan.getPw());
        parameters.put("todo", plan.getTodo());
        parameters.put("date", plan.getDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new PlanResponseDto(key.longValue(), plan.getName(), plan.getTodo(), plan.getDate());
    }

    @Override
    public List<PlanResponseDto> findAllPlans(Plan plan) {
        if (plan.getDate() != null && plan.getName() != null) {
            return jdbcTemplate.query(
                    "SELECT * from plan where substring(date, 1, 10) = ? and name = ? order by date desc",
                    planRowMapper(),
                    plan.getDate().substring(0, 10),
                    plan.getName()
            );
        } else if (plan.getDate() != null) {
            return jdbcTemplate.query(
                    "select * from plan where substring(date, 1, 10) = ? order by date desc",
                    planRowMapper(), plan.getDate().substring(0, 10)
            );
        } else if (plan.getName() != null) {
            return jdbcTemplate.query(
                    "select * from plan where name = ? order by date desc",
                    planRowMapper(), plan.getName()
            );
        } else {
            return jdbcTemplate.query(
                    "select * from plan order by date desc",
                    planRowMapper()
            );
        }
    }

    @Override
    public Plan findMemoByIdOrElseThrow(Long id) {
        List<Plan> result = jdbcTemplate.query("select * from plan where id = ?", planRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<Plan> planRowMapperV2() {
        return new RowMapper<Plan>() {
            @Override
            public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Plan(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("date")
                );
            }
        };
    }


    private RowMapper<PlanResponseDto> planRowMapper() {
        return new RowMapper<PlanResponseDto>() {
            @Override
            public PlanResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PlanResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("date")
                );
            }
        };
    }



}
