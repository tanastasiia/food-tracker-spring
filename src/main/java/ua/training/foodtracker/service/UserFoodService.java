package ua.training.foodtracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.dto.*;
import ua.training.foodtracker.entity.*;
import ua.training.foodtracker.exception.FoodNotExistsException;
import ua.training.foodtracker.exception.UserNotExistsException;
import ua.training.foodtracker.repository.UserFoodRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserFoodService {
    //TODO aytowired
    @Autowired
    private UserFoodRepository userFoodRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;

    private String getPrincipalUsername() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private boolean isLocaleUa() {
        return LocaleContextHolder.getLocale().equals(new Locale("ua"));
    }

    public UserFood save(UserMealDTO userMealDto) throws FoodNotExistsException, UserNotExistsException {

        Food food = foodService.findByName(userMealDto.getFoodName()).orElseThrow(FoodNotExistsException::new);
        User user = userService.findByUsername(getPrincipalUsername()).orElseThrow(UserNotExistsException::new);

        return userFoodRepository.save(UserFood.builder()
                .user(user)
                .food(food)
                .amount(userMealDto.getAmount())
                .dateTime(LocalDateTime.now())
                .build());
    }

    public MealsDTO findAllUsersFoodForAdmin(Pageable pageable) {

        Page<UserFood> page = userFoodRepository.findAll(pageable);

        List<MealDTO> meals = page.getContent().stream()
                .map(userFood -> MealDTO.builder()
                        .username(userFood.getUser().getUsername())
                        .foodName(isLocaleUa() ? userFood.getFood().getNameUa() : userFood.getFood().getName())
                        .amount(userFood.getAmount())
                        .date(userFood.getDateTime().toLocalDate())
                        .time(userFood.getDateTime().toLocalTime())
                        .build())
                .collect(Collectors.toList());

        return MealsDTO.builder().meals(new PageImpl<>(meals, pageable, page.getTotalElements())).build();
    }

    public UsersMealStatDTO findAllTodaysPrincipalStat(Pageable pageable) {
        Page<UserFood> page = userFoodRepository
                .findByUser_UsernameAndDateTimeBetween(getPrincipalUsername(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
                        pageable);

        List<UserMealStatDTO> todaysFood = page.getContent()
                .stream()
                .map(userFood -> UserMealStatDTO.builder()
                        .amount(userFood.getAmount())
                        .date(userFood.getDateTime().toLocalDate())
                        .time(userFood.getDateTime().toLocalTime())
                        .foodName(isLocaleUa() ? userFood.getFood().getNameUa() : userFood.getFood().getName())
                        .build())
                .collect(Collectors.toList());

        return UsersMealStatDTO.builder().usersFood(new PageImpl<>(todaysFood, pageable, page.getTotalElements())).build();
    }

    public UsersMealStatDTO findAllPrincipalStat(Pageable pageable) {

        Page<UserFood> page = userFoodRepository.findByUser_Username(getPrincipalUsername(), pageable);

        List<UserMealStatDTO> mealStat = page.getContent().stream()
                .map(userFood -> UserMealStatDTO.builder()
                        .amount(userFood.getAmount())
                        .date(userFood.getDateTime().toLocalDate())
                        .time(userFood.getDateTime().toLocalTime())
                        .foodName(isLocaleUa() ? userFood.getFood().getNameUa() : userFood.getFood().getName())
                        .build())
                .collect(Collectors.toList());

        return UsersMealStatDTO.builder().usersFood(new PageImpl<>(mealStat, pageable, page.getTotalElements())).build();
    }

    public UserTodayStatisticsDTO getTodaysPrincipalStatistics() throws UserNotExistsException {
        return UserTodayStatisticsDTO.builder()
                .calories(todaysCalories())
                .carbs(todaysFoodElement(Food::getCarbs))
                .protein(todaysFoodElement(Food::getProtein))
                .fat(todaysFoodElement(Food::getFat))
                .caloriesNorm(countCaloriesNorm())
                .build();
    }

    /**
     * Count daily calories norm for principal with formula
     */
//TODO
    public int countCaloriesNorm() throws UserNotExistsException {
        User user = userService.findByUsername(getPrincipalUsername())
                .orElseThrow(UserNotExistsException::new);

        return (int) (ActivityLevel.valueOf(user.getActivityLevel()).getValue()
                * (Gender.valueOf(user.getGender()).getValue()
                + 10 * user.getWeight()
                + 6.25 * user.getHeight()
                - 5 * user.getAge()));
    }

    private int todaysFoodElement(Function<Food, Integer> foodGetter) {
        return userFoodRepository
                .findByUser_UsernameAndDateTimeBetween(getPrincipalUsername(),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX))
                .stream()
                .mapToInt(userFood -> foodGetter.apply(userFood.getFood()) * userFood.getAmount() / 100)
                .reduce(Integer::sum).orElse(0);
    }

    public int todaysCalories() {
        return todaysFoodElement(Food::getCalories);
    }

}
