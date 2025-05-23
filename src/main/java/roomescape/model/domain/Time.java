package roomescape.model.domain;

import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Time {

    private final Long id;

    private final LocalTime time;

    public Time(LocalTime time) {
        this(null, time);
    }

    public Time withId(Long id) {
        return new Time(id, this.time);
    }
}
