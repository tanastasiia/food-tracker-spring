package ua.training.foodtracker.config;

import lombok.experimental.UtilityClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.training.foodtracker.entity.User;

import java.util.Locale;

@UtilityClass
public class Utils {

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public String getPrincipalUsername() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public boolean isLocaleUa() {
        return LocaleContextHolder.getLocale().equals(new Locale("ua"));
    }


}
