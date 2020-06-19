package ua.training.foodtracker.entity;

/**
 * Activity levels with coefficients for calories norm formula
 */
public enum ActivityLevel {
    FIRST(1.200),
    SECOND(1.375),
    THIRD(1.550),
    FORTH(1.725),
    FIFTH(1.900);

    private double value;

    ActivityLevel(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
