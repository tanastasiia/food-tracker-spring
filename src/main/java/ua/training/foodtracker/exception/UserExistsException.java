package ua.training.foodtracker.exception;

public class UserExistsException  extends Exception {
    public UserExistsException(){
        super("User exists");
    }
}
