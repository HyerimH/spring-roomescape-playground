package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 관리 페이지 반환
    @GetMapping("/reservation")
    public String Reservation(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservation";
    }

    // 예약 목록 조회
    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    // 예약 생성
    @PostMapping("/reservations")
    //@ResponseBody
    public ResponseEntity<ReservationResponse> addReservation(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.addReservation(request);
        return ResponseEntity
                .created(URI.create("/reservations/" + response.getId()))
                .body(response);
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
