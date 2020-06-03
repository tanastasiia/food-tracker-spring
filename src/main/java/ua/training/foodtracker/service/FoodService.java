package ua.training.foodtracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.training.foodtracker.dto.FoodDTO;
import ua.training.foodtracker.dto.FoodsDTO;
import ua.training.foodtracker.entity.Food;
import ua.training.foodtracker.repository.FoodRepository;

import java.util.Locale;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Optional<Food> findByName(String name) {
        if (LocaleContextHolder.getLocale().equals(new Locale("ua"))) {
            return foodRepository.findByNameUa(name);
        } else {
            return foodRepository.findByName(name);
        }
    }

    public Food save(FoodDTO foodDTO) {
        return foodRepository.save(Food.builder()
                .name(foodDTO.getName())
                .nameUa(foodDTO.getNameUa())
                .carbs(foodDTO.getCarbs())
                .protein(foodDTO.getProtein())
                .fat(foodDTO.getFat())
                .calories(foodDTO.getCalories())
                .build());
    }

    public FoodsDTO findAll(Pageable pageable) {
        return FoodsDTO.builder().foods(foodRepository.findAll(pageable)).build();
    }


}
