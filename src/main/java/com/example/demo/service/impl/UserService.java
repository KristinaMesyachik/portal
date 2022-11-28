package com.example.demo.service.impl;

import com.example.demo.dto.UserAddDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.MyUserDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.interf.IUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsernameEntity(username);
        return new MyUserDetails(user);
    }

    @Override
    public User findByUsernameEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + username + " not found"));
    }

    @Override
    public UserDTO findByUsername(String username) {
        return convertEntityToDto(findByUsernameEntity(username));
    }

    @Override
    public User saveEntity(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDTO saveNewUser(UserAddDTO userAddDTO) {
        User user = convertAddDtoToEntity(userAddDTO);
        return convertEntityToDto(saveEntity(user));
    }

    @Override
    public boolean changePassword(String username, String pass, String newPass) {
        User user = findByUsernameEntity(username);
        if (BCrypt.checkpw(pass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPass));
            saveEntity(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDTO update(UserDTO userDTO, String username) {
        User byUsername = findByUsernameEntity(username);
        User user = convertDtoToEntity(userDTO);
        user.setPassword(byUsername.getPassword());
        User save = userRepository.save(user);
        return convertEntityToDto(save);
    }

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password) {
        User user = findByUsernameEntity(username);
        return BCrypt.checkpw(password, user.getPassword());
    }

    @Override
    public UserDTO convertEntityToDto(User user) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User convertDtoToEntity(UserDTO userDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public User convertAddDtoToEntity(UserAddDTO userAddDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(userAddDTO, User.class);
    }
}
