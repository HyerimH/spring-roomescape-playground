package roomescape.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.domain.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@Repository
public class ReservationDAO {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet,
            rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getDate("date").toLocalDate(),
            new Time(resultSet.getLong("time_id"),
                    resultSet.getTime("time_value").toLocalTime())
    );


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservations() {
        String query = """
                SELECT r.id AS reservation_id, r.name, r.date,
                       t.id AS time_id, t.time AS time_value
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
                """;
        return namedParameterJdbcTemplate.query(query, RESERVATION_ROW_MAPPER);
    }

    public Reservation findReservationById(final Long id) {
        String query = """
                SELECT r.id AS reservation_id, r.name, r.date,
                       t.id AS time_id, t.time AS time_value
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
                WHERE r.id = :id
                """;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(query, parameters, RESERVATION_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
        }
    }

    public Long insertReservation(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time_id", reservation.getTimeId());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void deleteReservationById(final Long id) {
        String query = "DELETE FROM RESERVATION WHERE id = :id";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        int deletedCount = namedParameterJdbcTemplate.update(query, parameters);
        if (deletedCount == 0) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다: " + id);
        }
    }
}
