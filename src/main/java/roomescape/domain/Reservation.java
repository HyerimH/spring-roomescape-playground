package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {

    @EqualsAndHashCode.Include
    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;

    public Reservation(String name, LocalDate date, LocalTime time) {
        this(null, name, date, time);
    }
    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }
}
