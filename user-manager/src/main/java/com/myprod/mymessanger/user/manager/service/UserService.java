package com.myprod.mymessanger.user.manager.service;

import com.myprod.mymessanger.user.manager.entity.User;
import com.myprod.mymessanger.user.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
    }

    public User findUser(UUID uuid) {
        return userRepository.findById(uuid).get();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
