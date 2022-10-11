package com.example.demo.service.interf;

import com.example.demo.dto.UserAddDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService, IConvert<User, UserDTO> {
    UserDTO findByUsername(String username);

    boolean changePassword(String username, String pass, String newPass);

    UserDTO update(UserDTO user, String username);

    Boolean existsByUsernameAndPassword(String username, String password);

    UserDTO saveNewUser(UserAddDTO userAddDTO);

    User saveEntity(User user);

    User findByUsernameEntity(String username);

    User convertAddDtoToEntity(UserAddDTO userAddDTO);
}
