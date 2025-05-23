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
        return new ArrayList<>(reservations);
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Long newId = index.incrementAndGet();
        Reservation reservation = request.toReservation(newId);
        reservations.add(reservation);
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteReservation(Long id) {
        boolean isDeleted = reservations.removeIf(reservation -> reservation.hasId(id));
        if (!isDeleted) {
            throw new NotFoundException("해당 예약을 찾을 수 없습니다: " + id);
        }
    }
}
