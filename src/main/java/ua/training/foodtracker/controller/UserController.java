package ua.training.foodtracker.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.*;
import ua.training.foodtracker.dto.lists.FoodNamesDto;
import ua.training.foodtracker.dto.lists.UsersMealStatDto;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.FoodInfo;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.FoodExistsException;
import ua.training.foodtracker.exception.FoodNotExistsException;
import ua.training.foodtracker.exception.PasswordIncorrectException;
import ua.training.foodtracker.service.FoodInfoService;
import ua.training.foodtracker.service.FoodService;
import ua.training.foodtracker.service.MealService;
import ua.training.foodtracker.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Rest controller for users
 */
@Slf4j
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private UserService userService;
    private MealService mealService;
    private FoodService foodService;
    private FoodInfoService foodInfoService;
    private Utils utils;

    public UserController(MealService mealService, FoodService foodService, FoodInfoService foodInfoService,
                          UserService userService, Utils utils) {
        this.mealService = mealService;
        this.foodService = foodService;
        this.foodInfoService = foodInfoService;
        this.userService = userService;
        this.utils = utils;
    }

    /**
     * User info for account page
     *
     * @return user with localized fields
     * @see PageController#account()
     */
    @GetMapping("user_account")
    public UserDto userDto() {
        return userService.getLocalizedUserDto(utils.getPrincipal());
    }

    /**
     * User info for account change page
     *
     * @return principal user
     * @see PageController#accountChange()
     */
    @GetMapping("user")
    public UserRegDto user() {
        return userService.getUserRegDto(utils.getPrincipal());
    }

    /**
     * Today's food for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("todays_food")
    public UsersMealStatDto getTodaysFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllTodaysMeals(pageable, utils.getPrincipalId());
    }

    /**
     * All food for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("all_food")
    public UsersMealStatDto getAllFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllUserStat(pageable, utils.getPrincipalId());
    }

    /**
     * Today's food elements statistics for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("user_statistics")
    public UserTodayStatisticsDto getTodaysUserStatistics() {
        log.info("usert stat");
        return mealService.getTodaysUsersStatistics(utils.getPrincipal());
    }

    /**
     * Get data for calories chart
     *
     * @see PageController#statistics()
     */
    @GetMapping("calories_chart_data")
    public CaloriesChartData getCaloriesChartData(@RequestParam("days") int days) {
        log.info("calories_chart_data");
        return mealService.getCaloriesChartData(utils.getPrincipal(), days);
    }

    /**
     * Add meal in home page
     *
     * @return refreshed after meal amount of today's calories
     * @throws FoodNotExistsException if food not available for principal or not exists in database
     * @see PageController#home()
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("add_meal")
    public AddMealResponse addUserFood(@Valid UserMealDto userMealDto) throws FoodNotExistsException {
        FoodInfo foodInfo = foodInfoService.findFoodByFoodNameAndUser(userMealDto.getFoodName(), utils.getPrincipalId())
                .orElseThrow(FoodNotExistsException::new);
        log.info("Meal adding: {}", userMealDto.toString());
        return  mealService.save(foodInfo.getFood(), userMealDto.getAmount(), utils.getPrincipal());
    }

    /**
     * Add new food in home page
     *
     * @return localized success message
     * @see PageController#home()
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add_food")
    public MessageDto addFood(@Valid FoodDto foodDTO) throws FoodExistsException {
        log.info("Food adding: {}",foodDTO.toString());
        return foodInfoService.save(foodDTO, utils.getPrincipal());
    }

    /**
     * Change password in change password page
     *
     * @param passwordChangeDTO typed in old and new password
     * @throws PasswordIncorrectException if typed in old password passwords doesn't match the one in database
     * @see PageController#accountChangePassword()
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_password")
    public void changePassword(@Valid PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {
        userService.updatePassword(passwordChangeDTO, utils.getPrincipal());
        log.info("Password changed");
    }

    /**
     * Change account in account change page
     *
     * @see PageController#accountChange()
     */
   // @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_account")
    public void changeAccount(@Valid UserDto userDto) {
        User user = userService.updateAccount(utils.getPrincipal(), userDto);
        utils.updatePrincipal(user);
        log.info("Updated account: " + user.toString());
    }

    /**
     * For home page autocomplete
     *
     * @see PageController#home()
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("food_names_list")
    public FoodNamesDto foodNamesList() {
        log.info("Food names list ");
        return foodInfoService.foodNamesList(utils.getPrincipalId());
    }

}

