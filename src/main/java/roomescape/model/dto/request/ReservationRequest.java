package roomescape.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.model.domain.Reservation;
import roomescape.model.domain.Time;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotNull(message = "시간은 필수입니다.")
        Long time
) {

    public Reservation toReservation(Time time) {
        return new Reservation(name, date, time);
    }
}
