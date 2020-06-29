package ua.training.foodtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.foodtracker.entity.FoodInfo;

import java.util.List;
import java.util.Optional;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long> {

    @Query("SELECT foodInfo FROM FoodInfo foodInfo " +
            "WHERE (foodInfo.food.name=:foodName OR foodInfo.food.nameUa=:foodName)  " +
            "AND(foodInfo.user.id=:userId OR foodInfo.isGlobal=TRUE)")
    List<FoodInfo> findAllByFoodNameOrFoodNameUaAndUserIdOrGlobal(@Param("foodName") String foodName,
                                                                      @Param("userId") Long userId);

    List<FoodInfo> findAllByUser_IdOrIsGlobalTrue(Long userId);
}
