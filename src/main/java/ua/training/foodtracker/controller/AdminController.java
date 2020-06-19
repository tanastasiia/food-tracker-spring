package ua.training.foodtracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ua.training.foodtracker.dto.lists.FoodInfosDto;
import ua.training.foodtracker.dto.lists.MealsDto;
import ua.training.foodtracker.dto.lists.UsersDto;
import ua.training.foodtracker.service.FoodInfoService;
import ua.training.foodtracker.service.MealService;
import ua.training.foodtracker.service.UserService;

/**
 * Rest controller for admins
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private UserService userService;
    private FoodInfoService foodInfoService;
    private MealService mealService;

    public AdminController(UserService userService, FoodInfoService foodInfoService, MealService mealService) {
        this.userService = userService;
        this.foodInfoService = foodInfoService;
        this.mealService = mealService;
    }

    /**
     * Get all users for admin page
     *
     * @see PageController#admin()
     */
    @GetMapping("all_users")
    public UsersDto getAllUsers() {
        return userService.findAll();
    }

    /**
     * Gets all food for admin page
     *
     * @see PageController#admin()
     */
    @GetMapping("all_food")
    public FoodInfosDto getAllFood(@PageableDefault(sort = "food.name", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("all_food page: {}", pageable);
        return foodInfoService.findAll(pageable);
    }

    /**
     * Gets all users meals for admin page
     *
     * @see PageController#admin()
     */
    @GetMapping("all_meals")
    public MealsDto getAllUsersFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("all_meals page: {}", pageable);
        return mealService.findAllMeals(pageable);
    }

    /**
     * Change user role
     *
     * @param userId user which role is being changed
     * @param role   old user role
     * @see PageController#admin()
     */
    @PostMapping("change_role")
    public void changeRole(@RequestParam("userId") Long userId, @RequestParam("role") String role) {
        log.info("changing role " + role + "id: " + userId);
        userService.changeRole(userId, role);
    }


}
