package ua.training.foodtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.training.foodtracker.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    void updatePassword(@Param("newPassword") String newPassword, @Param("username") String username);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.role = :newRole WHERE u.id = :userId")
    void updateRole(@Param("newRole") String newRole, @Param("userId") Long userId);
}
