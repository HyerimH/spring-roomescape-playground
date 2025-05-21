package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private final String name;

    @NotNull(message = "날짜는 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    @NotNull(message = "시간은 필수입니다.")
    private final Long time;

    public Reservation toReservation(Time time) {
        return new Reservation(name, date, time);
    }
}
