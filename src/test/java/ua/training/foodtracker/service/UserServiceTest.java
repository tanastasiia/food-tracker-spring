package ua.training.foodtracker.service;


import com.oracle.jrockit.jfr.EventDefinition;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.config.SecurityConfiguration;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.data.UserTestData;
import ua.training.foodtracker.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserTestData userTestData;

    @InjectMocks
    ServiceUtils serviceUtils;

    SecurityConfiguration securityConfiguration = new SecurityConfiguration();
    LocaleConfiguration localeConfiguration = new LocaleConfiguration();
    UserService userService;

   /* @BeforeEach
    public void init() {
        userService = new UserService(userRepository, securityConfiguration, localeConfiguration, serviceUtils);
    }*/

    @Test
    public void findByUsernameTest() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(userTestData.USER));
        assertEquals(userService.findByUsername(userTestData.USER.getUsername()).get(), userTestData.USER);
    }

    @Test
    public void findByUsernameFailTest() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        assertFalse(userService.findByUsername(userTestData.USER.getUsername()).isPresent());
    }

    @Test
    public void getLocalizedUserUa() {
        LocaleContextHolder.setLocale(new Locale("ua"));
        assertEquals(userTestData.USER_DTO_UA, userService.getLocalizedUserDto(userTestData.USER));
    }

    @Test
    public void getLocalizedUserEng() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertEquals(userTestData.USER_DTO_ENG, userService.getLocalizedUserDto(userTestData.USER));
    }
}
