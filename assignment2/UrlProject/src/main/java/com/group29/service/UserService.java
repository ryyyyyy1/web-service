package com.group29.service;

import com.group29.bean.User;

import java.util.List;

/**
 * @author ryyyyyy
 * @create 2023-04-19 4:09
 */
public interface UserService {
    public List<User> getUserList();

    public User findUserById(long id);

    public void save(User user);

    public void edit(User user);

    public void delete(long id);

    public User login(String name, String pwd);

    public User register(String name, String pwd);
}
