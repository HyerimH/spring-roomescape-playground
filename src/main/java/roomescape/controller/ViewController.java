package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/reservation")
    public String Reservation() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String Time() {
        return "time";
    }
}
