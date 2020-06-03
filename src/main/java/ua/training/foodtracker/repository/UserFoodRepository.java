package ua.training.foodtracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.foodtracker.entity.UserFood;

import java.time.LocalDateTime;
import java.util.List;

public interface UserFoodRepository extends JpaRepository<UserFood, Long> {
    List<UserFood> findByUser_UsernameAndDateTimeBetween(String username, LocalDateTime dateTime1, LocalDateTime dateTime2);
//
    //List<UserFood> findAll();

    //Page<UserFood> findAll(Pageable pageable);

    Page<UserFood> findByUser_Username(String username, Pageable pageable);

    Page<UserFood> findByUser_UsernameAndDateTimeBetween(String username, LocalDateTime dateTime1,
                                                         LocalDateTime dateTime2,
                                                         Pageable pageable);


}
