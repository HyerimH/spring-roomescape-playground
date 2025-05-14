package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@RequiredArgsConstructor
public class Reservation {

    private final Long id;

    private final String name;

    private final LocalDate date;

    private final LocalTime time;

    public boolean hasId(Long id) {
        return Objects.equals(this.id, id);
    }
}
