package roomescape.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.model.domain.Time;

public record TimeRequest(
        @NotNull(message = "시간은 필수입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public Time toTime() {
        return new Time(time);
    }
}
