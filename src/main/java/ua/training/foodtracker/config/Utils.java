package ua.training.foodtracker.config;

import lombok.experimental.UtilityClass;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.training.foodtracker.entity.User;

import java.util.Locale;

@UtilityClass
public class Utils {

    private final Locale LOCALE_UA = new Locale("ua");

    public User getPrincipal() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void updatePrincipal(User user){
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
    }

    public Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public boolean isLocaleUa() {
        return LocaleContextHolder.getLocale().equals(LOCALE_UA);
    }


}
