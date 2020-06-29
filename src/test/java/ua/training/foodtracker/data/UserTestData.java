package ua.training.foodtracker.data;

import ua.training.foodtracker.config.SecurityConfiguration;
import ua.training.foodtracker.dto.UserDto;
import ua.training.foodtracker.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserTestData {

    private static SecurityConfiguration securityConfiguration = new SecurityConfiguration();

    private static final String PASSWORD_1 = securityConfiguration.getPasswordEncoder().encode("qwerty");
    private static final String PASSWORD_2 = securityConfiguration.getPasswordEncoder().encode("sdsd");
    private static final String PASSWORD_3 = securityConfiguration.getPasswordEncoder().encode("jkljl");
    private static final String PASSWORD_4 = securityConfiguration.getPasswordEncoder().encode("123qw");
    private static final String PASSWORD_5 = securityConfiguration.getPasswordEncoder().encode("123");

    public User ADMIN_USER = User.builder()
            .id(2L).weight(60).role("ROLE_ADMIN").height(170).gender("MALE").activityLevel("SECOND")
            .username("annat").dateOfBirth(LocalDate.now().minusYears(22)).lastName("Doe").firstName("Jane").password(PASSWORD_1).build();


    public User USER = User.builder()
            .id(2L).weight(60).role("ROLE_USER").height(170).gender("MALE").activityLevel("SECOND")
            .username("annat").dateOfBirth(LocalDate.now().minusYears(22)).lastName("Doe").firstName("Jane").password(PASSWORD_1).build();

    public UserDto USER_DTO_UA = UserDto.builder()
            .weight(60).height(170).gender("Чоловіча").activityLevel("Низький").username("annat")
            .dateOfBirth(LocalDate.now().minusYears(22).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .lastName("Doe").firstName("Jane").role("Користувач").build();

    public UserDto USER_DTO_ENG = UserDto.builder()
            .weight(60).height(170).gender("Male").activityLevel("Low").username("annat")
            .dateOfBirth(LocalDate.now().minusYears(22).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))
            .lastName("Doe").firstName("Jane").role("User").build();

    public User USER_2 = User.builder()
            .id(3L).weight(56).role("ROLE_USER").height(160).gender("MALE").activityLevel("FIRST")
            .username("dfgh").dateOfBirth(LocalDate.now().minusYears(32)).lastName("Jiel").firstName("Jake")
            .password(PASSWORD_2).build();


    public List<User> USERS_LIST = new ArrayList<>(Arrays.asList(
            User.builder()
                    .id(2L).weight(60).role("ROLE_ADMIN").height(170).gender("MALE").activityLevel("SECOND")
                    .username("annat").dateOfBirth(LocalDate.now().minusYears(22)).lastName("Doe").firstName("Jane")
                    .password(PASSWORD_1).build(),
            User.builder()
                    .id(3L).weight(56).role("ROLE_USER").height(160).gender("MALE").activityLevel("FIRST")
                    .username("dfgh").dateOfBirth(LocalDate.now().minusYears(32)).lastName("Jiel").firstName("Jake")
                    .password(PASSWORD_2).build(),
            User.builder()
                    .id(4L).weight(67).role("ROLE_USER").height(180).gender("MALE").activityLevel("FORTH")
                    .username("tommy").dateOfBirth(LocalDate.now().minusYears(42)).lastName("Ковальва").firstName("Юлія")
                    .password(PASSWORD_3).build(),
            User.builder()
                    .id(5L).weight(77).role("ROLE_USER").height(190).gender("MALE").activityLevel("FIFTH")
                    .username("fiveone").dateOfBirth(LocalDate.now().minusYears(34)).lastName("Bittlesome").firstName("Max")
                    .password(PASSWORD_4).build(),
            User.builder()
                    .id(6L).weight(50).role("ROLE_ADMIN").height(166).gender("FEMALE").activityLevel("THIRD")
                    .username("lrrred").dateOfBirth(LocalDate.now().minusYears(18)).lastName("Loganson").firstName("Carl")
                    .password(PASSWORD_5).build()
    ));

}
