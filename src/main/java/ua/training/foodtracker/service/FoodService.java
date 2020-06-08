package ua.training.foodtracker.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.lists.FoodsDto;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.repository.FoodRepository;

import java.util.Optional;

@Service
public class FoodService {

    private FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository){
        this.foodRepository= foodRepository;
    }

    public Optional<Food> findByName(String name) {
        if (Utils.isLocaleUa()) {
            return foodRepository.findByNameUa(name);
        } else {
            return foodRepository.findByName(name);
        }
    }

    public FoodsDto findAll(Pageable pageable) {
        return FoodsDto.builder().foods(foodRepository.findAll(pageable)).build();
    }


}
