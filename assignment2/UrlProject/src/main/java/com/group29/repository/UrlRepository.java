package com.group29.repository;

import com.group29.bean.UrlPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ryyyyyy
 * @create 2023-04-09 17:26
 *
 * a JPA interface, method define in this interface can automatically generate simple sql when called
 */
public interface UrlRepository extends JpaRepository<UrlPair, Long> {

    List<UrlPair> findAllByUserName(String userName);

    //select by the id in the database
    UrlPair findById(long id);

    //delete by the id in the database
    void deleteById(Long id);

    //find the object by the field shortUrl in the database
    UrlPair findByShortUrl(String shortUrl);
}
