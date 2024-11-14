package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.entity.User;
import org.example.testsecurity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
        }
    }
}
