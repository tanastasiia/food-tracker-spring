package ua.training.foodtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.foodtracker.entity.Food;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByName(String name);

    Optional<Food> findByNameUa(String name);

}
