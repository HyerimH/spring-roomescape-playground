package roomescape.dao;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@Component
public class ReservationDAO {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @PostConstruct
    public void createReservation() {
        final var query = "CREATE TABLE RESERVATION(id long, name varchar(100), date varchar(19), time varchar(8));";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addReservation(final Reservation reservation) {
        final var query = "INSERT INTO RESERVATION VALUES(?, ? , ?, ?)";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, reservation.getId());
            preparedStatement.setString(2, reservation.getName());
            preparedStatement.setString(3, reservation.getDate().toString());
            preparedStatement.setString(4, reservation.getTime().toString());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation findReservationById(final Long id) {
        final var query = "SELECT * FROM RESERVATION WHERE id = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
    }

    public List<Reservation> findAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        final var query = "SELECT * FROM RESERVATION";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query);
                final var resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("date")),
                        LocalTime.parse(resultSet.getString("time"))
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }

    public void updateReservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        final var query = "UPDATE RESERVATION SET name = ?, date = ?, time = ?  WHERE id = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setString(3, time.toString());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteReservation(final Long id) {
        final var query = "DELETE FROM RESERVATION WHERE id = ?";
        try (
                final var connection = getConnection();
                final var preparedStatement = connection.prepareStatement(query)
        ) {
            Reservation reservation = findReservationById(id);
            if (reservation == null) {
                throw new NotFoundException("해당 Reservation을 찾을 수 없습니다. ID: " + id);
            }
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
