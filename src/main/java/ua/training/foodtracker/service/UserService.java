package ua.training.foodtracker.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.foodtracker.config.LocaleConfiguration;
import ua.training.foodtracker.config.SecurityConfiguration;
import ua.training.foodtracker.dto.UserDTO;
import ua.training.foodtracker.dto.UsersDTO;
import ua.training.foodtracker.entity.Role;
import ua.training.foodtracker.entity.User;
import ua.training.foodtracker.exception.UserNotExistsException;
import ua.training.foodtracker.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private LocaleConfiguration localeConfiguration;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDTO getUserDTOByUsername(String username) throws UserNotExistsException {

        User user = userRepository.findByUsername(username).orElseThrow(UserNotExistsException::new);

        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .height(user.getHeight())
                .weight(user.getWeight())
                .activityLevel(localeConfiguration.getMessageResource()
                        .getMessage(user.getActivityLevel(), null, LocaleContextHolder.getLocale()))
                .age(user.getAge())
                .firstName(user.getFirstName())
                .firstNameUa(user.getFirstNameUa())
                .gender(user.getGender()).build();

    }

    @Transactional
    public User save(UserDTO userDto) {
        return userRepository.save(
                User.builder()
                        .username(userDto.getUsername())
                        .active(true)
                        .password(securityConfiguration.getPasswordEncoder().encode(userDto.getPassword()))
                        .firstName(userDto.getFirstName())
                        .firstNameUa(userDto.getFirstNameUa())
                        .roles(Role.ROLE_USER.name())
                        .height(userDto.getHeight())
                        .weight(userDto.getWeight())
                        .activityLevel(userDto.getActivityLevel())
                        .age(userDto.getAge())
                        .gender(userDto.getGender())
                        .build()
        );
    }

    public UsersDTO findAll() {
        return UsersDTO.builder().users(userRepository.findAll()).build();

    }

    @Transactional
    public void updatePassword(String newPassword, String username) {
        userRepository.updatePassword(securityConfiguration.getPasswordEncoder()
                .encode(newPassword), username);
    }

}
