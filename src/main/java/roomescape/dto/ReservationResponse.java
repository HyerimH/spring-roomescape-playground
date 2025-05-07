package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {

    private Long id;

    private String name;

    private LocalDate date;

    private LocalTime time;

    public ReservationResponse(Long id, ReservationRequest request) {
        this.id = id;
        this.name = request.getName();
        this.date = request.getDate();
        this.time = request.getTime();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
