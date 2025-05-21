package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import roomescape.exception.NotFoundException;

@Repository
public class TimeDAO {

    private static final RowMapper<Time> TIME_ROW_MAPPER = (resultSet, rowNum)
            -> new Time(
            resultSet.getLong("id"),
            resultSet.getTime("time").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAllTimes() {
        String query = "SELECT id, time FROM TIME";
        return jdbcTemplate.query(query, TIME_ROW_MAPPER);
    }

    public Time findTimeById(final Long id) {
        String query = "SELECT * FROM TIME WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, TIME_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Time을 찾을 수 없습니다. ID: " + id);
        }
    }

    public Long insertTime(final Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime().toString());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void deleteTime(final Long id) {
        String query = "DELETE FROM TIME WHERE id = ?";
        int update = jdbcTemplate.update(query, id);
        if (update == 0) {
            throw new NotFoundException("해당 Time을 찾을 수 없습니다: " + id);
        }
    }
}
