package roomescape.service.dto.response;

import java.time.LocalTime;

public record TimeResponse(
        Long id,
        LocalTime time
) {

}
