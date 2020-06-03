package ua.training.foodtracker.exception;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException() {
        super("Password incorrect");
    }
}