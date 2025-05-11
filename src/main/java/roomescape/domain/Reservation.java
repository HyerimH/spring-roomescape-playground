package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;
}
