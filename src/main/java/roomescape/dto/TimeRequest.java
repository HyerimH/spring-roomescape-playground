package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.domain.Time;

@Getter
@NoArgsConstructor
public class TimeRequest {

    @NotNull(message = "시간은 필수입니다.")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Time toTime() {
        return new Time(time);
    }
}
