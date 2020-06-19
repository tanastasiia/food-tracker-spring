package ua.training.foodtracker.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.config.SecurityConfiguration;
import ua.training.foodtracker.config.Utils;
import ua.training.foodtracker.dto.PasswordChangeDto;
import ua.training.foodtracker.dto.UserDto;
import ua.training.foodtracker.dto.UserRegDto;
import ua.training.foodtracker.dto.lists.UsersDto;
import ua.training.foodtracker.entity.Role;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.PasswordIncorrectException;
import ua.training.foodtracker.exception.UserExistsException;
import ua.training.foodtracker.exception.UserNotExistsException;
import ua.training.foodtracker.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.rmi.ServerException;
import java.util.Optional;

/**
 * Service for {@link User} entity
 */
@Slf4j
@Service
public class UserService {


    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;
    private LocaleConfiguration localeConfiguration;

    @PersistenceContext
    private EntityManager entityManager;

    public UserService(UserRepository userRepository, SecurityConfiguration securityConfiguration, LocaleConfiguration localeConfiguration) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        this.localeConfiguration = localeConfiguration;
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by username
     */
    public UserDto getLocalizedUserDto(User user) {

        return UserDto.builder()
                .username(user.getUsername())
                .height(user.getHeight())
                .weight(user.getWeight())
                .activityLevel(localeConfiguration.getMessageResource()
                        .getMessage(user.getActivityLevel(), null, LocaleContextHolder.getLocale()))
                .age(user.getAge())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(localeConfiguration.getMessageResource()
                        .getMessage(user.getGender(), null, LocaleContextHolder.getLocale())).build();

    }
    /**
     * Find user by username
     */
    public UserDto getUserDto(User user) {
        return new UserDto(user);

    }

    /**
     * Register new user
     *
     * @param userRegDto new user info
     * @return saved user
     * @throws UserExistsException if user with such username already exists
     */
    public User register(UserRegDto userRegDto) throws UserExistsException {
        if (findByUsername(userRegDto.getUsername()).isPresent()) {
            log.info("UserExistsException throwing");
            throw new UserExistsException();
        }

        return userRepository.save(
                User.builder()
                        .username(userRegDto.getUsername())
                        .password(securityConfiguration.getPasswordEncoder().encode(userRegDto.getPassword()))
                        .firstName(userRegDto.getFirstName())
                        .lastName(userRegDto.getLastName())
                        .role(Role.ROLE_USER.name())
                        .height(userRegDto.getHeight())
                        .weight(userRegDto.getWeight())
                        .activityLevel(userRegDto.getActivityLevel())
                        .age(userRegDto.getAge())
                        .gender(userRegDto.getGender())
                        .build()
        );
    }

    /**
     * Find all users
     */
    public UsersDto findAll() {
        return UsersDto.builder().users(userRepository.findAll()).build();

    }

    /**
     * Change password of user to opposite
     *
     * @param passwordChangeDTO user, old and new password
     */
    @Transactional
    public void updatePassword(PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {

        if (!securityConfiguration.getPasswordEncoder()
                .matches(passwordChangeDTO.getOldPassword(), Utils.getPrincipal().getPassword())) {
            throw new PasswordIncorrectException();
        }

        userRepository.updatePassword(securityConfiguration.getPasswordEncoder()
                .encode(passwordChangeDTO.getNewPassword()), Utils.getPrincipal().getUsername());
    }

    /**
     * Change role of user to opposite
     *
     * @param userId of user whose role is being changed
     * @param role   current user role
     */
    @Transactional
    public void changeRole(Long userId, String role) {
        userRepository.updateRole(role.equals(Role.ROLE_ADMIN.name()) ? Role.ROLE_USER.name() : Role.ROLE_ADMIN.name(), userId);
    }

    /**
     * Change user info
     *
     * @param user updated user info
     */
    @Transactional
    public void updateAccount(User user, UserDto userDto) {

        entityManager.merge(User.builder()
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .height(userDto.getHeight())
                .weight(userDto.getWeight())
                .activityLevel(userDto.getActivityLevel())
                .age(userDto.getAge())
                .gender(userDto.getGender())
                .build());
    }

}
