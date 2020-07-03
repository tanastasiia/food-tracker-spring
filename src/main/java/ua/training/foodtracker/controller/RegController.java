package ua.training.foodtracker.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.training.foodtracker.dto.UserRegDto;
import ua.training.foodtracker.exception.UserExistsException;
import ua.training.foodtracker.service.UserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/registration/")
public class RegController {

    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public void register(UserRegDto userRegDto) throws UserExistsException {
        log.info("Saved user: {}", userService.register(userRegDto));
    }

}
