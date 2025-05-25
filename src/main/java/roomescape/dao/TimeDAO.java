package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAllTimes() {
        String query = "SELECT id, time FROM TIME";
        return namedParameterJdbcTemplate.query(query, TIME_ROW_MAPPER);
    }

    public Time findTimeById(final Long id) {
        String query = "SELECT * FROM time WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, parameters, TIME_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Time을 찾을 수 없습니다. ID: " + id);
        }
    }

    public Long insertTime(final Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("time", time.getTime().toString());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void deleteTimeById(final Long id) {
        String query = "DELETE FROM TIME WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        int deletedCount = namedParameterJdbcTemplate.update(query, parameters);
        if (deletedCount == 0) {
            throw new NotFoundException("해당 Time을 찾을 수 없습니다: " + id);
        }
    }
}
