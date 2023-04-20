package com.group29.service;

import com.group29.repository.UserRepository;
import com.group29.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void edit(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User login(String name, String pwd) {
        User userDB = userRepository.findByNameAndPwd(name, pwd);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("fail to login...");
    }

    @Override
    public User register(String name, String pwd) {
        User userDB = userRepository.findByName(name);
        System.out.println("111111111111");
        if (userDB == null) {
            User user = new User();
            user.setName(name);
            user.setPwd(pwd);
            this.save(user);
            return user;
        }
        throw new RuntimeException("duplicate user...");
    }
}


