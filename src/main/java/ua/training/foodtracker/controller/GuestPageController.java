package ua.training.foodtracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class GuestPageController {

    @GetMapping("/login")
    public String login() {
        log.info("login page");
        return "login";
    }

    @GetMapping("/registration")
    public String reg() {
        log.info("registration page");
        return "registration.html";
    }

    @GetMapping("/denied")
    public String denied() {
        log.info("denied page");
        return "access_denied";
    }
}
