package ua.training.foodtracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.training.foodtracker.exception.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserExistsException.class})
    public void userExists() {
        log.info("UserExistsException handled");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotExistsException.class)
    public void userNotExists() {
        log.info("UserNotExistsException handled");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FoodExistsException.class)
    public void foodExists() {
        log.info("FoodExistsException handled");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FoodNotExistsException.class)
    public void foodNotExists() {
        log.info("FoodNotExistsException handled");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordIncorrectException.class)
    public void passwordIncorrect() {
        log.info("PasswordIncorrectException handled");
    }
}
