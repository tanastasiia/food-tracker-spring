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
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.FoodNotExistsException;
import ua.training.foodtracker.exception.PasswordIncorrectException;
import ua.training.foodtracker.exception.UserNotExistsException;
import ua.training.foodtracker.service.FoodInfoService;
import ua.training.foodtracker.service.FoodService;
import ua.training.foodtracker.service.MealService;
import ua.training.foodtracker.service.UserService;


@Slf4j
@RestController
@RequestMapping("/api/user/")
public class UserController {

    private MealService mealService;
    private FoodService foodService;
    private FoodInfoService foodInfoService;
    private UserService userService;

    public UserController(MealService mealService, FoodService foodService, FoodInfoService foodInfoService, UserService userService) {
        this.mealService = mealService;
        this.foodService = foodService;
        this.foodInfoService = foodInfoService;
        this.userService = userService;
    }


    @GetMapping("user_account")
    public UserDto userDto() throws UserNotExistsException {
        return userService.getUserDTOByUsername( Utils.getPrincipalUsername());
    }
    @GetMapping("user")
    public User user() {
        return Utils.getPrincipal();
        //return userService.findByUsername(Utils.getPrincipalUsername()).orElseThrow(UserNotExistsException::new);
    }

    @GetMapping("todays_food")
    public UsersMealStatDto getTodaysFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllTodaysMeals(pageable, Utils.getPrincipalId());
    }

    @GetMapping("all_food")
    public UsersMealStatDto getAllFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return mealService.findAllPrincipalStat(pageable, Utils.getPrincipalId());
    }

    @GetMapping("user_statistics")
    public UserTodayStatisticsDto getTodaysUserStatistics(){
        log.info("usert stat");
        return mealService.getTodaysPrincipalStatistics(Utils.getPrincipalId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("add_meal")
    public int addUserFood(UserMealDto userMealDto) throws FoodNotExistsException {

        log.info("Meal dto: {}",  userMealDto.getFoodName());
        Food food = foodService.findByName(userMealDto.getFoodName()).orElseThrow(FoodNotExistsException::new);

        log.info("Meal added: {}", mealService.save(food, userMealDto.getAmount()));

        return mealService.todaysCalories();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add_food")
    public void addFood(FoodDto foodDTO){

        log.info("Food added: {}", foodInfoService.save(foodDTO, Utils.getPrincipalId()));

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_password")
    public void changePassword(PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {

        userService.updatePassword(passwordChangeDTO);
        log.info("Password changed");
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("change_account")
    public void changeAccount(User user){

       userService.updateAccount(user);
        log.info("Update account: " + user.toString());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("food_names_list")
    public FoodNamesDto foodNamesList(){

        log.info("Food names list " );
        return foodInfoService.foodNamesList(Utils.getPrincipalId());
    }

}

