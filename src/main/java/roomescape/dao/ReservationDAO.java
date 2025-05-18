package roomescape.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class ReservationDAO {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            LocalTime.parse(resultSet.getString("time"))
    );

    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        String query = "SELECT id, name, date, time FROM RESERVATION";
        List<Reservation> reservations = jdbcTemplate.query(query, RESERVATION_ROW_MAPPER);
        return new ArrayList<>(reservations);
    }

    public Reservation findReservationById(final Long id) {
        String query = "SELECT * FROM RESERVATION WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, RESERVATION_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
        }
    }
}
