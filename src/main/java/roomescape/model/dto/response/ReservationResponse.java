package roomescape.model.dto.response;

import java.time.LocalDate;
import roomescape.model.domain.Reservation;
import roomescape.model.domain.Time;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        Time time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
