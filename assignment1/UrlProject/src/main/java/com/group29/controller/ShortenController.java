package com.group29.controller;

import com.group29.bean.UrlPair;
import com.group29.service.UrlService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author ryyyyyy
 * @create 2023-04-09 15:11
 *
 * this is the controller to deal with the url shorten service
 */
@Controller
public class ShortenController {

    @Resource
    UrlService service;

    @Resource
    UrlService urlService;

    //delete method with no id, return 404
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String delete(){
        return "404, not found!";
    }
    //post method receive the long url and generate a shortened one, and store this pair into database
    @PostMapping("/url")
    @ResponseStatus(HttpStatus.CREATED)
    public String getUrl(@RequestBody String url, Model model) {
        if (url.length()==4){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your url must not be null!");
        }
        //springboot will still do the auto escape even we disable the auto decode, so we have to do this manually
        url = url.replaceAll("%2F","/");
        url = url.replaceAll("%3A",":");

        //we deal both conditions that the input url starts with "http" or "https", and the url resembles just "xxx.xxx.xxx"
        if (url.startsWith("url=http://")){
            url = url.substring(7);
        } else if (url.startsWith("url=https://")){
            url = url.substring(8);
        }
        System.out.println(url);
        String originalUrl = url.substring(4);
        //we shorten the url by randomly choose 6 characters in the base62 charSet
        String charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stringBuilder = new StringBuilder(6);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(charSet.length());
            stringBuilder.append(charSet.charAt(index));
        }
        String base62Url = stringBuilder.toString();
        UrlPair urlPair1 = urlService.findByShort(base62Url);
        //if the shortened url has already been used, we generate another one
        if (urlPair1 != null){
            for (int i = 0; i < 6; i++) {
                StringBuilder stringBuilder1 = new StringBuilder(6);
                int index = random.nextInt(charSet.length());
                stringBuilder1.append(charSet.charAt(index));
                base62Url = stringBuilder1.toString();
            }
        }
//        String shortUrl = basicUrl + base62Url;
        UrlPair urlPair = new UrlPair();
//        urlPair.setShortUrl(shortUrl);
        urlPair.setShortUrl(base62Url);
        urlPair.setLongUrl(originalUrl);

        urlService.save(urlPair);
        model.addAttribute("shortUrl",base62Url);
        return "index";
    }

    //handle the get request, provide a redirect service and return 301 if the shortUrl is valid
    //otherwise, if the shortUrl doesn't exist in the database, return 404
    @GetMapping("/g29")
    @ResponseStatus(HttpStatus.PERMANENT_REDIRECT)
    @ResponseBody
    public RedirectView Redirect(String shortUrl){

        if (service.findByShort(shortUrl) == null){
            RedirectView redirectView = new RedirectView("404.html");
            redirectView.setStatusCode(HttpStatus.NOT_FOUND);
            return redirectView;
        }
        String originalUrl = urlService.findLongByShort(shortUrl);
        System.out.println(originalUrl);
        RedirectView redirectView = new RedirectView("http://" + originalUrl,true);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        //mark this visit of this url, and store it
        UrlPair urlPair = urlService.findByShort(shortUrl);
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY){urlPair.setMon(urlPair.getMon()+1);}
        if (dayOfWeek == DayOfWeek.TUESDAY){urlPair.setTue(urlPair.getTue()+1);}
        if (dayOfWeek == DayOfWeek.WEDNESDAY){urlPair.setWed(urlPair.getWed()+1);}
        if (dayOfWeek == DayOfWeek.THURSDAY){urlPair.setThu(urlPair.getThu()+1);}
        if (dayOfWeek == DayOfWeek.FRIDAY){urlPair.setFri(urlPair.getFri()+1);}
        if (dayOfWeek == DayOfWeek.SATURDAY){urlPair.setSat(urlPair.getSat()+1);}
        if (dayOfWeek == DayOfWeek.SUNDAY){urlPair.setSun(urlPair.getSun()+1);}
        urlService.save(urlPair);
        return redirectView;
    }

}
