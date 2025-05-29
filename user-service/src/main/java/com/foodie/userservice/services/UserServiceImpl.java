package com.foodie.userservice.services;

import com.foodie.userservice.dto.UserDTO;
import com.foodie.userservice.entity.User;
import com.foodie.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        User user = new User(null, dto.getName(), dto.getEmail(), false);
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