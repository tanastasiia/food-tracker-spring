package ua.training.foodtracker.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.training.foodtracker.dto.UserDTO;
import ua.training.foodtracker.exception.UserExistsException;
import ua.training.foodtracker.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/registration/")
public class RegController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("register")
    public void register(UserDTO userDto) throws UserExistsException {

        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            log.info("UserExistsException throwing");
            throw new UserExistsException();
        }
        log.info("Saved user: {}", userService.save(userDto));
    }

}
