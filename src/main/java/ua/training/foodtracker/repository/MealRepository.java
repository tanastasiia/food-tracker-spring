package ua.training.foodtracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.foodtracker.entity.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByUser_IdAndDateTimeBetween(Long userId, LocalDateTime dateTime1, LocalDateTime dateTime2);

    Page<Meal> findByUser_IdAndDateTimeBetween(Long userId,
                                               LocalDateTime dateTime1,
                                               LocalDateTime dateTime2,
                                               Pageable pageable);

    Page<Meal> findByUser_Id(Long userId, Pageable pageable);


}
