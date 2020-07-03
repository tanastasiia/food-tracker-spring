package ua.training.foodtracker.controller;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.config.SecurityConfiguration;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.data.FoodInfoTestData;
import ua.training.foodtracker.data.MealTestData;
import ua.training.foodtracker.data.UserTestData;
import ua.training.foodtracker.entity.Meal;
import ua.training.foodtracker.repository.FoodInfoRepository;
import ua.training.foodtracker.repository.MealRepository;
import ua.training.foodtracker.repository.UserRepository;
import ua.training.foodtracker.service.FoodInfoService;
import ua.training.foodtracker.service.MealService;
import ua.training.foodtracker.service.ServiceUtils;
import ua.training.foodtracker.service.UserService;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MealRepository mealRepository;
    @Mock
    private FoodInfoRepository foodInfoRepository;

    @InjectMocks
    private Utils utils;

    @InjectMocks
    UserTestData userTestData;
    @InjectMocks
    MealTestData mealTestData;
    @InjectMocks
    FoodInfoTestData foodInfoTestData;

    SecurityConfiguration securityConfiguration = new SecurityConfiguration();
    LocaleConfiguration localeConfiguration = new LocaleConfiguration();
    ServiceUtils serviceUtils = new ServiceUtils(localeConfiguration);
    UserService userService;


    MealService mealService;
    FoodInfoService foodInfoService;

    /*@BeforeEach
    public void init() {
        utils.updatePrincipal(userTestData.USER);
        userService = new UserService(userRepository, securityConfiguration, localeConfiguration, serviceUtils);
        mealService = new MealService(mealRepository, serviceUtils, localeConfiguration);
        foodInfoService = new FoodInfoService(foodInfoRepository, serviceUtils, localeConfiguration);

    }*/

    @Test
    public void getUaUserDtoTest() throws Exception {
        this.mockMvc.perform(get("/api/user/user_account?lang=ua")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value(userTestData.USER_DTO_UA.getUsername()))
                .andExpect(jsonPath("$.dateOfBirth").value(userTestData.USER_DTO_UA.getDateOfBirth()))
                .andExpect(jsonPath("$.activityLevel").value(userTestData.USER_DTO_UA.getActivityLevel()))
                .andExpect(jsonPath("$.gender").value(userTestData.USER_DTO_UA.getGender()));
    }

    @Test
    public void getTodaysFoodUaTest() throws Exception {
        Mockito.when(mealRepository.findByUser_IdAndDateTimeBetween(Mockito.anyLong(),
                Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(mealTestData.TODAYS_MEALS_LIST));

        this.mockMvc.perform(get("/api/user/todays_food?lang=ua").characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.usersFood.totalElements").value(mealTestData.TODAYS_MEALS_LIST.size()))
                .andExpect(jsonPath("$.usersFood.content[0].foodName").value(mealTestData.TODAYS_MEALS_LIST.get(0).getFood().getNameUa()));
    }

    /*

    @GetMapping("all_food")
    public UsersMealStatDto getAllFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllPrincipalStat(pageable, utils.getPrincipalId());
    }

    @GetMapping("user_statistics")
    public UserTodayStatisticsDto getTodaysUserStatistics() {
        log.info("usert stat");
        return mealService.getTodaysPrincipalStatistics(utils.getPrincipal());
    }

    @GetMapping("calories_chart_data")
    public CaloriesChartData getCaloriesChartData(@RequestParam("days") int days) {
        log.info("calories_chart_data");
        return mealService.getCaloriesChartData(utils.getPrincipal(), days);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("add_meal")
    public int addUserFood(UserMealDto userMealDto) throws FoodNotExistsException {
        Food food = foodService.findByName(userMealDto.getFoodName()).orElseThrow(FoodNotExistsException::new);
        log.info("Meal added: {}", mealService.save(food, userMealDto.getAmount(), utils.getPrincipal()));
        return mealService.todaysCalories(utils.getPrincipalId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add_food")
    public void addFood(FoodDto foodDTO) {
        log.info("Food added: {}", foodInfoService.save(foodDTO, utils.getPrincipal()));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_password")
    public void changePassword(PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {
        userService.updatePassword(passwordChangeDTO, utils.getPrincipal());
        log.info("Password changed");
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_account")
    public void changeAccount(UserDto userDto) {
        User user = userService.updateAccount(utils.getPrincipal(), userDto);
        utils.updatePrincipal(user);
        log.info("Updated account: " + user.toString());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("food_names_list")
    public FoodNamesDto foodNamesList() {
        log.info("Food names list ");
        return foodInfoService.foodNamesList(utils.getPrincipalId());
    }
     */

}
