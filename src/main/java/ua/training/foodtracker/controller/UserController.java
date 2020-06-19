package ua.training.foodtracker.controller;


import lombok.extern.slf4j.Slf4j;
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
import ua.training.foodtracker.exception.FoodNotExistsException;
import ua.training.foodtracker.exception.PasswordIncorrectException;
import ua.training.foodtracker.service.FoodInfoService;
import ua.training.foodtracker.service.FoodService;
import ua.training.foodtracker.service.MealService;
import ua.training.foodtracker.service.UserService;


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

    public UserController(MealService mealService, FoodService foodService, FoodInfoService foodInfoService, UserService userService) {
        this.mealService = mealService;
        this.foodService = foodService;
        this.foodInfoService = foodInfoService;
        this.userService = userService;
    }

    /**
     * User info for account page
     *
     * @return user with localized fields
     * @see PageController#account()
     */
    @GetMapping("user_account")
    public UserDto userDto() {
        return userService.getLocalizedUserDto(Utils.getPrincipal());
    }

    /**
     * User info for account change page
     *
     * @return principal user
     * @see PageController#accountChange()
     */
    @GetMapping("user")
    public UserDto user() {
        return userService.getUserDto(Utils.getPrincipal());
    }

    /**
     * Today's food for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("todays_food")
    public UsersMealStatDto getTodaysFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllTodaysMeals(pageable, Utils.getPrincipalId());
    }

    /**
     * All food for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("all_food")
    public UsersMealStatDto getAllFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllPrincipalStat(pageable, Utils.getPrincipalId());
    }

    /**
     * Today's food elements statistics for statistics page
     *
     * @see PageController#statistics()
     */
    @GetMapping("user_statistics")
    public UserTodayStatisticsDto getTodaysUserStatistics() {
        log.info("usert stat");
        return mealService.getTodaysPrincipalStatistics(Utils.getPrincipal());
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
    public int addUserFood(UserMealDto userMealDto) throws FoodNotExistsException {
        Food food = foodService.findByName(userMealDto.getFoodName()).orElseThrow(FoodNotExistsException::new);
        log.info("Meal added: {}", mealService.save(food, userMealDto.getAmount(), Utils.getPrincipal()));
        return mealService.todaysCalories();
    }

    /**
     * Add new food in home page
     *
     * @see PageController#home()
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add_food")
    public void addFood(FoodDto foodDTO) {
        log.info("Food added: {}", foodInfoService.save(foodDTO, Utils.getPrincipal()));
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
    public void changePassword(PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {
        userService.updatePassword(passwordChangeDTO);
        log.info("Password changed");
    }

    /**
     * Change account in account change page
     *
     * @see PageController#accountChange()
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_account")
    public void changeAccount(UserDto user) {
        userService.updateAccount(Utils.getPrincipal(), user);
        log.info("Update account: " + user.toString());
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
        return foodInfoService.foodNamesList(Utils.getPrincipalId());
    }

}

