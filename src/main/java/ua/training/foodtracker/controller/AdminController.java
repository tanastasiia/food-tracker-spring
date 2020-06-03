package ua.training.foodtracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.training.foodtracker.dto.FoodsDTO;
import ua.training.foodtracker.dto.MealsDTO;
import ua.training.foodtracker.dto.UsersDTO;
import ua.training.foodtracker.service.FoodService;
import ua.training.foodtracker.service.UserFoodService;
import ua.training.foodtracker.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserFoodService userFoodService;

    @GetMapping("all_users")
    public UsersDTO getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("all_food")
    public FoodsDTO getAllFood(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {

        log.info("all_food page: {}", pageable);
        return foodService.findAll(pageable);
    }

    @GetMapping("all_users_food")
    public MealsDTO getAllUsersFood(@PageableDefault(sort = "dateTime", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("all_users_food page: {}", pageable);
        return userFoodService.findAllUsersFoodForAdmin(pageable);
    }


}
