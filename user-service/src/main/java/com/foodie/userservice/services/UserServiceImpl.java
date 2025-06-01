package com.foodie.userservice.services;

import com.foodie.userservice.dto.UserDTO;
import com.foodie.userservice.entity.User;
import com.foodie.userservice.exception.DuplicateUserException;
import com.foodie.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO dto) {
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("A user with this email already exists");
        }

        User user = new User(dto.getName(), dto.getEmail(), true);
        user = userRepository.save(user);

        dto.setId(user.getId());
        return dto;
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }
}