package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.JoinRequestDTO;
import org.example.testsecurity.entity.User;
import org.example.testsecurity.exception.ErrorCode;
import org.example.testsecurity.exception.customExceptions.UserDoesntExistException;
import org.example.testsecurity.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public boolean joinProcess(JoinRequestDTO joinDTO) {

        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser) {
            return false;
        }

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
        return true;
    }

    public void deleteUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserDoesntExistException("User doesnt exist", ErrorCode.USER_DOESNT_EXIST));
        userRepository.delete(user);
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
