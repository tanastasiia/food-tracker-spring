package ua.training.foodtracker.service;

import lombok.experimental.UtilityClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.entity.ActivityLevel;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.Gender;
import ua.training.foodtracker.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
public class ServiceUtils {

    private final Locale LOCALE_UA = new Locale("ua");

    public static final Integer GRAMS_TO_MILLIGRAMS = 1000;
    public static final BigDecimal MILLIGRAMS_TO_GRAMS = BigDecimal.valueOf(1000d);

    public static final String ADD_FOOD_SUCCESS = "messages.alert.food.added";
    public static final String ADD_MEAL_SUCCESS = "messages.alert.meal.added";

    public static DateTimeFormatter dayMonthDateFormat = DateTimeFormatter.ofPattern("dd.MM");
    public static DateTimeFormatter dateFormatUa = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static DateTimeFormatter dateFormatEng = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    private LocaleConfiguration localeConfiguration;

    public ServiceUtils(LocaleConfiguration localeConfiguration) {
        this.localeConfiguration = localeConfiguration;
    }

    /**
     * Converts grams to milligrams
     *
     * @param grams grams to convert
     * @return milligrams
     */
    public Integer toMilligrams(BigDecimal grams) {
        return grams.multiply(BigDecimal.valueOf(GRAMS_TO_MILLIGRAMS)).intValue();
    }

    /**
     * Converts milligrams to grams
     *
     * @param milligrams milligrams to convert
     * @return grams
     */
    public BigDecimal toGrams(Integer milligrams) {
        return BigDecimal.valueOf(milligrams)
                .setScale(2, RoundingMode.HALF_UP).divide(MILLIGRAMS_TO_GRAMS, 2);
    }

    /**
     * Returns localized food name
     *
     * @param food Food which name is returned
     * @return name in needed locale (ukrainian or english) if  name in that locale exists in database and in another language if not.
     * Returns empty string if both names are absent.
     */
    public String getLocalizedFoodName(Food food) {
        Optional<String> nameUa = Optional.ofNullable(food.getNameUa());
        Optional<String> name = Optional.ofNullable(food.getName());
        return isLocaleUa()
                ? nameUa.orElse(name.orElse(""))
                : name.orElse(nameUa.orElse(""));
    }

    /**
     * Returns localized date
     *
     * @param date date to localize
     * @return localized date
     */
    public String getLocalizedDate(LocalDate date) {
        return isLocaleUa()
                ? date.format(dateFormatUa)
                : date.format(dateFormatEng);
    }

    /**
     * Parse localized date
     *
     * @param date date to localize
     * @return localized date
     */
    public LocalDate parseLocalizedDate(String date) {
        return isLocaleUa()
                ? LocalDate.parse(date, dateFormatUa)
                : LocalDate.parse(date, dateFormatEng);
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

    public boolean isLocaleUa() {
        return LocaleContextHolder.getLocale().equals(LOCALE_UA);
    }

    public String getLocalizedMessage(String key) {
        return localeConfiguration.getMessageResource()
                .getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
