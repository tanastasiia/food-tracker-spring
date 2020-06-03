package ua.training.foodtracker.exception;

public class FoodExistsException extends Exception {
    public FoodExistsException(){
        super("Food exists");
    }
}
