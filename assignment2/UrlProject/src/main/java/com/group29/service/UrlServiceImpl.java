package com.group29.service;

import com.group29.bean.UrlPair;
import com.group29.bean.User;
import com.group29.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ryyyyyy
 * @create 2023-04-09 17:29
 *
 * this class implements the interface UrlService, and we use UrlRepository interface to automatically generate sql
 */
@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;
    private String shortUrl;

    @Override
    public List<UrlPair> getPairList() {
        return urlRepository.findAll();
    }

    @Override
    public void save(UrlPair urlPair) {
        urlRepository.save(urlPair);
    }

    @Override
    public void edit(UrlPair urlPair) {
        urlRepository.save(urlPair);
    }

    @Override
    public UrlPair findUrlById(long id) {
        return urlRepository.findById(id);
    }

    @Override
    public UrlPair findByShort(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }


//    @Override
//    public void editUrl() {
//
//    }

    @Override
    public void deleteUrlById(long id) {
        urlRepository.deleteById(id);
    }

    @Override
    public String findLongByShort(String shortUrl) {
        UrlPair urlPair = urlRepository.findByShortUrl(shortUrl);
        String longUrl = urlPair.getLongUrl();
        return longUrl;
    }

    @Override
    public List<Long> getViews(long id) {
        UrlPair urlPair = urlRepository.findById(id);
        List<Long> views = new ArrayList<Long>();
        views.add(urlPair.getMon());
        views.add(urlPair.getTue());
        views.add(urlPair.getWed());
        views.add(urlPair.getThu());
        views.add(urlPair.getFri());
        views.add(urlPair.getSat());
        views.add(urlPair.getSun());
        System.out.println("views=" + views.toString());
        return views;
    }

}
