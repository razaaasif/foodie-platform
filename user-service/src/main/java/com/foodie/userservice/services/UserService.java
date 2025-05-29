package com.foodie.userservice.services;

import com.foodie.userservice.dto.UserDTO;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUser(Long id);
}
