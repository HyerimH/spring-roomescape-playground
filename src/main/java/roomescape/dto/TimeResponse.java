package roomescape.dto;

import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TimeResponse {

    private final Long id;

    private final LocalTime time;
}
