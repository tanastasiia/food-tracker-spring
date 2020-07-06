package ua.training.foodtracker.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for {@link User} entity
 */
@Slf4j
@Service
public class UserService  implements UserDetailsService {


    private UserRepository userRepository;
    private SecurityConfiguration securityConfiguration;
    private ServiceUtils serviceUtils;

    @PersistenceContext
    private EntityManager entityManager;

    public UserService(UserRepository userRepository, SecurityConfiguration securityConfiguration,
                       ServiceUtils serviceUtils) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
        this.serviceUtils = serviceUtils;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("username " + username + " not found"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by username
     */
    public UserDto getLocalizedUserDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .height(user.getHeight())
                .weight(user.getWeight())
                .activityLevel(serviceUtils.getLocalizedMessage(user.getActivityLevel()))
                .dateOfBirth(serviceUtils.getLocalizedDate(user.getDateOfBirth()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(serviceUtils.getLocalizedMessage(user.getRole()))
                .gender(serviceUtils.getLocalizedMessage(user.getGender())).build();

    }

    /**
     * Find user by username
     */
    public UserRegDto getUserRegDto(User user) {
        return new UserRegDto(user);
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
                        .dateOfBirth(LocalDate.parse(userRegDto.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE))
                        .gender(userRegDto.getGender())
                        .build()
        );
    }

    /**
     * Find all users
     */
    public UsersDto findAll() {
        return UsersDto.builder()
                .users(userRepository.findAll().stream().map(this::getLocalizedUserDto).collect(Collectors.toList()))
                .build();
    }

    /**
     * Change password of user to opposite
     *
     * @param passwordChangeDTO user, old and new password
     */
    @Transactional
    public void updatePassword(PasswordChangeDto passwordChangeDTO, User user) throws PasswordIncorrectException {

        if (!securityConfiguration.getPasswordEncoder()
                .matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new PasswordIncorrectException();
        }

        userRepository.updatePassword(securityConfiguration.getPasswordEncoder()
                .encode(passwordChangeDTO.getNewPassword()), user.getUsername());
    }

    /**
     * Change role of user to opposite
     *
     * @param userId of user whose role is being changed
     */
    @Transactional
    public void changeRole(Long userId) {
        User user = entityManager.find(User.class, userId);
        userRepository.updateRole(user.getRole().equals(Role.ROLE_ADMIN.name()) ? Role.ROLE_USER.name() : Role.ROLE_ADMIN.name(), userId);
    }

    /**
     * Change user info
     *
     * @param user updated user info
     */
    @Transactional
    public User updateAccount(User user, UserDto userDto) {

        User newUser = User.builder()
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .height(userDto.getHeight())
                .weight(userDto.getWeight())
                .activityLevel(userDto.getActivityLevel())
                .dateOfBirth(LocalDate.parse(userDto.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE))
                .gender(userDto.getGender())
                .build();

        return entityManager.merge(newUser);
    }

}
