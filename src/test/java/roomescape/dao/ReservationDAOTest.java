package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@SpringBootTest
class ReservationDAOTest {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private static Reservation defaultReservation;

    @BeforeEach
    void setUp() {
        defaultReservation = new Reservation(1L, "testReservation", LocalDate.parse("2023-08-05"),
                LocalTime.parse("19:25"));
        reservationDAO.addReservation(defaultReservation);
    }

    @Test
    void connection() {
        try (final var connection = reservationDAO.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addReservation() {
        Reservation readReservation = reservationDAO.findReservationById(defaultReservation.getId());
        assertThat(readReservation).isEqualTo(defaultReservation);
    }

    @Test
    void findReservationById() {
        final var readReservation = reservationDAO.findReservationById(defaultReservation.getId());
        assertThat(readReservation).isEqualTo(defaultReservation);
    }

    @Test
    void updateReservation() {
        final var updatedReservation = new Reservation(defaultReservation.getId(), "testReservation2",
                LocalDate.parse("2024-08-05"),
                LocalTime.parse("20:25"));
        reservationDAO.updateReservation(updatedReservation.getId(), updatedReservation.getName(),
                updatedReservation.getDate(), updatedReservation.getTime());

        Reservation readReservation = reservationDAO.findReservationById(defaultReservation.getId());
        assertThat(readReservation).isEqualTo(updatedReservation);
    }

    @Test
    void deleteReservation() {
        reservationDAO.deleteReservation(defaultReservation.getId());
        assertThatThrownBy(() -> reservationDAO.findReservationById(defaultReservation.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
