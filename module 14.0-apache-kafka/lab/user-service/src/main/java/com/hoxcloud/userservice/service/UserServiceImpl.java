package com.hoxcloud.userservice.service;

import com.hoxcloud.userservice.dto.UserRequest;
import com.hoxcloud.userservice.dto.UserResponse;
import com.hoxcloud.userservice.entity.User;
import com.hoxcloud.userservice.entity.UserStatus;
import com.hoxcloud.userservice.exception.UserNotFoundException;
import com.hoxcloud.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .status(UserStatus.ACTIVE)
                .build();

        return toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return toResponse(findUser(id));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = findUser(id);
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPhone(request.phone());

        return toResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus()
        );
    }
}
