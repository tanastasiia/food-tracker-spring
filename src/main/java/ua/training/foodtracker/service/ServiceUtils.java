package ua.training.foodtracker.service;

import lombok.experimental.UtilityClass;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.entity.Food;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@UtilityClass
public class ServiceUtils {

    public static final Integer GRAMS_TO_MILLIGRAMS = 1000;
    public static final BigDecimal MILLIGRAMS_TO_GRAMS = BigDecimal.valueOf(1000d);

    public Integer toMilligrams(BigDecimal grams) {
        return grams.multiply(BigDecimal.valueOf(GRAMS_TO_MILLIGRAMS)).intValue();
    }

    public BigDecimal toGrams(Integer milligrams) {
        return BigDecimal.valueOf(milligrams)
                .setScale(2, RoundingMode.HALF_UP).divide(MILLIGRAMS_TO_GRAMS, 2);
    }

    public String getLocalizedName(Food food) {
        Optional<String> nameUa = Optional.ofNullable(food.getNameUa());
        Optional<String> name = Optional.ofNullable(food.getName());
        return Utils.isLocaleUa()
                ? nameUa.orElse(name.orElse(""))
                : name.orElse(nameUa.orElse(""));
    }
}
