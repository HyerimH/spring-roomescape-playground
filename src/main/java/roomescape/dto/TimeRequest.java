package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;

@Getter
public class TimeRequest {

    @NotNull(message = "시간은 필수입니다.")
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    @JsonCreator
    public TimeRequest(@JsonProperty("time") LocalTime time) {
        this.time = time;
    }

    public Time toTime() {
        return new Time(time);
    }
}
