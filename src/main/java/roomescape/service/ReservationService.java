package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationResponse createReservation(ReservationRequest request) {
        Time time = timeDAO.findTimeById(request.time());
        Reservation reservation = request.toReservation(time);
        Long newId = reservationDAO.insertReservation(reservation);
        Reservation savedReservation = reservation.withId(newId);
        return ReservationResponse.from(savedReservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAllReservations();
    }

    public Reservation getReservationById(Long id) {
        return reservationDAO.findReservationById(id);
    }

    public void deleteReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}


