package com.hoxcloud.lab.service;

import com.hoxcloud.lab.dto.SignUpDto;
import com.hoxcloud.lab.dto.UserDto;
import com.hoxcloud.lab.entity.UserEntity;
import com.hoxcloud.lab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
private final UserRepository userRepository;
private final ModelMapper modelMapper;
private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new AuthenticationException("Bad Credential") {
        });

    }

    public UserDto singUp(SignUpDto signUpDto) {
     Optional<UserEntity> user=userRepository.findByEmail(signUpDto.getEmail());
     if(user.isPresent()){ throw new BadCredentialsException("User or Email Id is wrong ");
     }
     UserEntity toBeSaveUser=modelMapper.map(signUpDto,UserEntity.class);
     toBeSaveUser.setPassword(passwordEncoder.encode(toBeSaveUser.getPassword()));
     return modelMapper.map(userRepository.save(toBeSaveUser),UserDto.class);
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
