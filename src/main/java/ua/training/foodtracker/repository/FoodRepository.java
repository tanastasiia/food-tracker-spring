package ua.training.foodtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.foodtracker.entity.Food;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

}
