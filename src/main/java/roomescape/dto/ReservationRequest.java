package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public class ReservationRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @NotNull(message = "시간은 필수입니다.")
    private LocalTime time;

    public ReservationRequest(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
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

    public Reservation toEntity(Long id) {
        return new Reservation(id, name, date, time);
    }
}
