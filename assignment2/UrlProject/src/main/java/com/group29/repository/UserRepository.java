package com.group29.repository;

import com.group29.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ryyyyyy
 * @create 2023-04-19 4:02
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long userId);
    void deleteById(Long id);

    User findByNameAndPwd(String name,String pwd);

    User findByName(String name);
}
