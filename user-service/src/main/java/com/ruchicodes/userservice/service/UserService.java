package com.ruchicodes.userservice.service;

import com.ruchicodes.userservice.dto.UserRequest;
import com.ruchicodes.userservice.dto.UserResponse;
import com.ruchicodes.userservice.entity.User;
import com.ruchicodes.userservice.exception.DuplicateEmailException;
import com.ruchicodes.userservice.exception.UserNotFoundException;
import com.ruchicodes.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    //create user
    @Transactional
    public UserResponse createUser(UserRequest request){
        String  email = normalizeEmail(request.email());
        if (userRepository.existsByEmail(email)){
            throw new DuplicateEmailException("User already exists with email: "+email);
        }
        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPhone(request.phone());

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);

    }

    //get all users
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //get user by id
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found with id: "+id));
        return mapToResponse(user);
    }

    //update user
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request){
      User user = userRepository.findById(id)
              .orElseThrow(()->
                      new UserNotFoundException("User not found with id: "+id));
      String email = normalizeEmail(request.email());
      if (userRepository.existsByEmailAndIdNot(email,id)){
          throw new DuplicateEmailException("Email already used by another user: "+email);
      }
      user.setName(request.name().trim());
      user.setEmail(email);
      user.setPhone(request.phone());
      User updatedUser = userRepository.save(user);
      return mapToResponse(updatedUser);
    }

    //delete user
    @Transactional
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User not found with id: "+id));
        userRepository.delete(user);
    }

    private String normalizeEmail(String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }
    private UserResponse mapToResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
