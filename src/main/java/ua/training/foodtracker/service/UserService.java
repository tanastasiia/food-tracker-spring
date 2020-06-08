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
import ua.training.foodtracker.dto.lists.UsersDto;
import ua.training.foodtracker.entity.Role;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.PasswordIncorrectException;
import ua.training.foodtracker.exception.UserExistsException;
import ua.training.foodtracker.exception.UserNotExistsException;
import ua.training.foodtracker.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

    public UserDto getUserDTOByUsername(String username) throws UserNotExistsException {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotExistsException::new);

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .height(user.getHeight())
                .weight(user.getWeight())
                .activityLevel(localeConfiguration.getMessageResource()
                        .getMessage(user.getActivityLevel(), null, LocaleContextHolder.getLocale()))
                .age(user.getAge())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender()).build();

    }

   // @Transactional ???
    public User save(UserDto userDto) throws UserExistsException {
        if (findByUsername(userDto.getUsername()).isPresent()) {
            log.info("UserExistsException throwing");
            throw new UserExistsException();
        }

        return userRepository.save(
                User.builder()
                        .username(userDto.getUsername())
                        .password(securityConfiguration.getPasswordEncoder().encode(userDto.getPassword()))
                        .firstName(userDto.getFirstName())
                        .lastName(userDto.getLastName())
                        .role(Role.ROLE_USER.name())
                        .height(userDto.getHeight())
                        .weight(userDto.getWeight())
                        .activityLevel(userDto.getActivityLevel())
                        .age(userDto.getAge())
                        .gender(userDto.getGender())
                        .build()
        );
    }

    public UsersDto findAll() {
        return UsersDto.builder().users(userRepository.findAll()).build();

    }

    @Transactional
    public void updatePassword(PasswordChangeDto passwordChangeDTO) throws PasswordIncorrectException {

        if (!securityConfiguration.getPasswordEncoder()
                .matches(passwordChangeDTO.getOldPassword(), Utils.getPrincipal().getPassword())) {
            throw new PasswordIncorrectException();
        }

        userRepository.updatePassword(securityConfiguration.getPasswordEncoder()
                .encode(passwordChangeDTO.getNewPassword()), Utils.getPrincipal().getUsername());
    }

    @Transactional
    public void changeRole(Long userId, String role){
        userRepository.updateRole(role.equals(Role.ROLE_ADMIN.name())? Role.ROLE_USER.name() :Role.ROLE_ADMIN.name(), userId  );
    }

    @Transactional
    public void updateAccount(User user){
        entityManager.merge(user);
    }

}
