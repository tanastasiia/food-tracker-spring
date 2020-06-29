package ua.training.foodtracker.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.lists.FoodsDto;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.repository.FoodRepository;

import java.util.Optional;

/**
 * Service for {@link Food} entity
 */
@Service
public class FoodService {

    private FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

}
