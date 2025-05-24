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
        parameters.put("modi", plan.getModi());
        parameters.put("userId", plan.getUserId()); // Lv3

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new PlanResponseDto(key.longValue(), plan.getName(), plan.getTodo(), plan.getDate(), plan.getModi(), plan.getUserId());
    }

    @Override
    public List<PlanResponseDto> findAllPlans(Plan plan) {
        if (plan.getUserId() != null) {
            return jdbcTemplate.query(
                    "select * from plan where userId = ? ORDER BY modi DESC",
                    planRowMapper(), plan.getUserId()
            );
        }
        if (plan.getModi() != null && plan.getName() != null) {
            return jdbcTemplate.query(
                    "select * from plan where substring(modi, 1, 10) = ? and name = ? order by modi desc",
                    planRowMapper(),
                    plan.getModi().substring(0, 10),
                    plan.getName()
            );
        } else if (plan.getModi() != null) {
            return jdbcTemplate.query(
                    "select * from plan where substring(modi, 1, 10) = ? order by modi desc",
                    planRowMapper(), plan.getModi().substring(0, 10)
            );
        } else if (plan.getName() != null) {
            return jdbcTemplate.query(
                    "select * from plan where name = ? order by modi desc",
                    planRowMapper(), plan.getName()
            );
        } else {
            return jdbcTemplate.query(
                    "select * from plan order by modi desc",
                    planRowMapper()
            );
        }
    }
    private RowMapper<PlanResponseDto> planRowMapper() {
        return new RowMapper<PlanResponseDto>() {
            @Override
            public PlanResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PlanResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getString("date"),
                        rs.getString("modi"),
                        rs.getLong("userId")
                );
            }
        };
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
                        rs.getString("date"),
                        rs.getString("modi")

                );
            }
        };
    }




    @Override
    public int updatePlan(Long id, String pw, String name, String todo, String current) {
        String dbPw = jdbcTemplate.queryForObject("select pw from plan where id = ?", String.class, id);
        if (!pw.equals(dbPw)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return jdbcTemplate.update("update plan set name = ?, todo = ?, modi = ? where id = ?", name, todo, current, id);
    }

    @Override
    public int deletePlan(Long id, String pw) {
        String dbPw = jdbcTemplate.queryForObject("select pw from plan where id = ?", String.class, id);
        if (!pw.equals(dbPw)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        return jdbcTemplate.update("delete from plan where id = ?", id);
    }
}
