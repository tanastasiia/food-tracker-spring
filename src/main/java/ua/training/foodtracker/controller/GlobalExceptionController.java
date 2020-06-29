package ua.training.foodtracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.dto.MessageDto;
import ua.training.foodtracker.dto.PasswordChangeDto;
import ua.training.foodtracker.dto.ValidationErrorResponse;
import ua.training.foodtracker.exception.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionController {

    private LocaleConfiguration localeConfiguration;

    public GlobalExceptionController(LocaleConfiguration localeConfiguration) {
        this.localeConfiguration = localeConfiguration;
    }

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
    public ResponseEntity<MessageDto> foodExists() {
        log.info("FoodExistsException handled");
        return new ResponseEntity<>(
                MessageDto.builder()
                        .message(localeConfiguration.getMessageResource()
                                .getMessage("messages.error.such.food.in.db", null, LocaleContextHolder.getLocale())).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FoodNotExistsException.class)
    public ResponseEntity<MessageDto> foodNotExists() {
        log.info("FoodNotExistsException handled");
        return new ResponseEntity<>(
                MessageDto.builder()
                        .message(localeConfiguration.getMessageResource()
                                .getMessage("messages.error.no.such.food.in.db", null, LocaleContextHolder.getLocale())).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordIncorrectException.class)
    public void passwordIncorrect() {
        log.info("PasswordIncorrectException handled");
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleMethodArgumentNotValid(BindException ex) {

        System.out.println("BindException EXC");
        ex.getAllErrors().forEach(System.out::println);
        return new ResponseEntity<>(
                ex.getFieldErrors().stream()
                        .map(error ->
                                new ValidationErrorResponse(
                                        localeConfiguration.getMessageResource()
                                                .getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, LocaleContextHolder.getLocale()),
                                        error.getField())
                        )
                        .collect(Collectors.toList()),
                HttpStatus.NOT_ACCEPTABLE
        );
    }


}
