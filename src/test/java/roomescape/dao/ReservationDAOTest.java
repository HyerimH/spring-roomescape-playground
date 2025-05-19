package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundException;

@SpringBootTest
class ReservationDAOTest {

    @Autowired
    private ReservationDAO reservationDAO;

    private Reservation insertedReservation;

    @BeforeEach
    void setUp() {
        Reservation reservation = new Reservation("testReservation", LocalDate.parse("2023-08-05"),
                LocalTime.parse("19:25"));
        Long id = reservationDAO.insertReservation(reservation);
        insertedReservation = reservation.withId(id);
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
    void deleteReservation() {
        reservationDAO.deleteReservation(insertedReservation.getId());
        assertThatThrownBy(() -> reservationDAO.findReservationById(insertedReservation.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
