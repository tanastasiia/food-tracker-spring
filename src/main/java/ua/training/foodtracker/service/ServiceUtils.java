package ua.training.foodtracker.service;

import lombok.experimental.UtilityClass;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.entity.ActivityLevel;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.Gender;
import ua.training.foodtracker.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class ServiceUtils {

    public static final Integer GRAMS_TO_MILLIGRAMS = 1000;
    public static final BigDecimal MILLIGRAMS_TO_GRAMS = BigDecimal.valueOf(1000d);

    public static DateTimeFormatter dayMonthDateFormat = DateTimeFormatter.ofPattern("dd/MM");

    /**
     * Converts grams to milligrams
     * @param grams grams to convert
     * @return milligrams
     */
    public Integer toMilligrams(BigDecimal grams) {
        return grams.multiply(BigDecimal.valueOf(GRAMS_TO_MILLIGRAMS)).intValue();
    }

    /**
     * Converts milligrams to grams
     * @param milligrams milligrams to convert
     * @return grams
     */
    public BigDecimal toGrams(Integer milligrams) {
        return BigDecimal.valueOf(milligrams)
                .setScale(2, RoundingMode.HALF_UP).divide(MILLIGRAMS_TO_GRAMS, 2);
    }

    /**
     * Returns localized food name
     * @param food Food which name is returned
     * @return name in needed locale (ukrainian or english)if  name in that locale exists in database and in another language if not.
     * Returns empty string if both names are absent.
     */
    public String getLocalizedFoodName(Food food) {
        Optional<String> nameUa = Optional.ofNullable(food.getNameUa());
        Optional<String> name = Optional.ofNullable(food.getName());
        return Utils.isLocaleUa()
                ? nameUa.orElse(name.orElse(""))
                : name.orElse(nameUa.orElse(""));
    }

    /**
     * Counts calories norm for user by the Mifflin St Jeor equation
     */
    public Integer countCaloriesNorm(User user) {
        return (int) (ActivityLevel.valueOf(user.getActivityLevel()).getValue()
                * (Gender.valueOf(user.getGender()).getValue()
                + 10 * user.getWeight()
                + 6.25 * user.getHeight()
                - 5 * Period.between(user.getDateOfBirth(), LocalDate.now()).getYears()));
    }
}
