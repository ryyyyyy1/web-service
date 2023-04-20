package com.group29.service;

import com.group29.bean.UrlPair;
import com.group29.bean.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ryyyyyy
 * @create 2023-04-09 17:29
 *
 * this is the interface defined to handle the database service
 */
@Service
public interface UrlService {

    public List<UrlPair> getPairList();

    //add to the database
    public void save(UrlPair urlPair);

    //edit the exiting object
    public void edit(UrlPair urlPair);

    public UrlPair findUrlById(long id);
    public UrlPair findByShort(String shortUrl);

    public void deleteUrlById(long id);

    public String findLongByShort(String shortUrl);

    //get all the visits of an object, the result will be store in a list
    public List<Long> getViews(long id);

}
