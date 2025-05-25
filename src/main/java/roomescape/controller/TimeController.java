package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Time;
import roomescape.service.dto.request.TimeRequest;
import roomescape.service.dto.response.TimeResponse;
import roomescape.service.TimeService;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes() {
        return ResponseEntity.ok(timeService.getAllTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@Valid @RequestBody TimeRequest request) {
        TimeResponse response = timeService.createTime(request);
        return ResponseEntity
                .created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
