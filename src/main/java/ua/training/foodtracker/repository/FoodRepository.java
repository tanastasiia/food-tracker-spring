package ua.training.foodtracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.foodtracker.entity.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByName(String name);

    Optional<Food> findByNameUa(String name);

    List<Food> findAll();

    Page<Food> findAll(Pageable pageable);
}
