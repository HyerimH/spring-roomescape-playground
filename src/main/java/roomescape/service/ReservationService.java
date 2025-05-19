package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = request.toReservation();
        Long newId = reservationDAO.insertReservation(reservation);
        Reservation savedReservation = reservation.withId(newId);
        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getName(),
                savedReservation.getDate(),
                savedReservation.getTime()
        );
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


