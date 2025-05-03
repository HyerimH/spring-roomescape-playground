package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

  private List<Reservation> reservations = new ArrayList<>();

  public ReservationController() {
    reservations.add(new Reservation(1L, "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")));
    reservations.add(new Reservation(2L, "옐로우", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00")));
    reservations.add(new Reservation(3L, "레드", LocalDate.parse("2023-01-03"), LocalTime.parse("12:00")));
  }

  // 예약 관리 페이지 반환
  @GetMapping("/reservation")
  public String Reservation(Model model) {
    model.addAttribute("reservations", reservations);
    return "reservation";
  }

  // 예약 목록 조회
  @GetMapping("/reservations")
  @ResponseBody
  public List<Reservation> getReservations() {
    return reservations;
  }
}
