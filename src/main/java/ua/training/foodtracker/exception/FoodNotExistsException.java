package ua.training.foodtracker.exception;

public class FoodNotExistsException extends Exception {
    public FoodNotExistsException(){
        super("Food does not exist");
    }
}
