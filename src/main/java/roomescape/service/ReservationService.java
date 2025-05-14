package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final AtomicLong index = new AtomicLong(0);
    private final ReservationDAO reservationDAO;


    public List<Reservation> getAllReservations() {
        return reservationDAO.findAllReservations();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Long newId = index.incrementAndGet();
        Reservation reservation = request.toReservation(newId);
        reservationDAO.addReservation(reservation);
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationDAO.findReservationById(id);
        if (reservation == null) {
            throw new NotFoundException("해당 예약을 찾을 수 없습니다: " + id);
        }
        reservationDAO.deleteReservation(id);
    }
}
