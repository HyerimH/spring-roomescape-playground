package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservations() {
        String query = "SELECT id, name, date, time FROM RESERVATION";
        return jdbcTemplate.query(query, RESERVATION_ROW_MAPPER);
    }

    public Reservation findReservationById(final Long id) {
        String query = "SELECT * FROM RESERVATION WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, RESERVATION_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
        }
    }

    public Long insertReservation(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate().toString());
        parameters.put("time", reservation.getTime().toString());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public void deleteReservation(final Long id) {
        String query = "DELETE FROM RESERVATION WHERE id = ?";
        int update = jdbcTemplate.update(query, id);
        if (update == 0) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다: " + id);
        }
    }
}
