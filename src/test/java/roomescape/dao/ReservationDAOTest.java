package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.model.domain.Reservation;
import roomescape.model.domain.Time;
import roomescape.exception.NotFoundException;

@JdbcTest
@Import({ReservationDAO.class, TimeDAO.class})
class ReservationDAOTest {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private TimeDAO timeDAO;

    private Reservation insertedReservation;

    @BeforeEach
    void setUp() {
        Time time = new Time(LocalTime.parse("19:25"));
        Long timeId = timeDAO.insertTime(time);
        Time savedTime = time.withId(timeId);

        Reservation reservation = new Reservation("testReservation", LocalDate.parse("2023-08-05"), savedTime);
        Long reservationId = reservationDAO.insertReservation(reservation);
        insertedReservation = reservation.withId(reservationId);
    }

    @Test
    void insertReservation() {
        assertThat(reservationDAO.findAllReservations()).contains(insertedReservation);
    }

    @Test
    void findAllReservations() {
        assertThat(reservationDAO.findAllReservations()).contains(insertedReservation);
    }

    @Test
    void findReservationById() {
        Reservation found = reservationDAO.findReservationById(insertedReservation.getId());
        assertThat(found).isEqualTo(insertedReservation);
    }

    @Test
    void deleteReservationById() {
        reservationDAO.deleteReservationById(insertedReservation.getId());
        assertThatThrownBy(() -> reservationDAO.findReservationById(insertedReservation.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
