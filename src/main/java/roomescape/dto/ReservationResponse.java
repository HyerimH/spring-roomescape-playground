package roomescape.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Time;

@Getter
@RequiredArgsConstructor
public class ReservationResponse {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final Time time;
}
