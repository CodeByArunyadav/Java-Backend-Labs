package com.hoxcloud.lab.service;

import com.hoxcloud.lab.config.PermissionMapping;
import com.hoxcloud.lab.dto.SignUpDto;
import com.hoxcloud.lab.dto.UserDto;
import com.hoxcloud.lab.entity.Roles;
import com.hoxcloud.lab.entity.UserEntity;
import com.hoxcloud.lab.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsManager {
private final UserRepository userRepository;
private final ModelMapper modelMapper;
private final PasswordEncoder passwordEncoder;
  // private final PermissionMapping permissionMapping;

    public UserDto singUp(SignUpDto signUpDto) {
     Optional<UserEntity> user=userRepository.findByEmail(signUpDto.getEmail());
     if(user.isPresent()){ throw new BadCredentialsException("User already registered !!");
     }
     UserEntity toBeSaveUser=modelMapper.map(signUpDto,UserEntity.class);
     toBeSaveUser.setPassword(passwordEncoder.encode(toBeSaveUser.getPassword()));
     toBeSaveUser.setRoles(Set.of(Roles.USER));
     UserEntity savedUser = userRepository.save(toBeSaveUser);

     return modelMapper.map(savedUser, UserDto.class);
     //return modelMapper.map(userRepository.save(toBeSaveUser),UserDto.class);
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                userRepository.findByEmail(username).orElseThrow(()->new AuthenticationException("Bad Credential") {
        });

    }

    public UserEntity getUserByEmailId(String userEmail) {

        return userRepository.findByEmail(userEmail).orElseThrow();
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
