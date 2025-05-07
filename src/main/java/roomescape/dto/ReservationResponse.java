package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class ReservationResponse {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;

    public ReservationResponse(Long id, ReservationRequest request) {
        this.id = id;
        this.name = request.getName();
        this.date = request.getDate();
        this.time = request.getTime();
    }
}
