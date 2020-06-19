package ua.training.foodtracker.entity;

/**
 * Genders with coefficients for calories norm formula
 */
public enum Gender {
    FEMALE(-161),
    MALE(5);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
