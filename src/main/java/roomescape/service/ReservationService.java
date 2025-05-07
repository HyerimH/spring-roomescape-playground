package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NotFoundException;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Long newId = index.incrementAndGet();
        Reservation reservation = request.toEntity(newId);
        reservations.add(reservation);
        return new ReservationResponse(newId, request);
    }

    public void deleteReservation(Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new NotFoundException("해당 예약을 찾을 수 없습니다: " + id);
        }
    }
}
