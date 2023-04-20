package com.group29.controller;

import com.group29.bean.UrlPair;
import com.group29.service.UrlService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
 * @author ryyyyyy
 * @create 2023-04-09 21:04
 *
 * this is the controller to handle other service except shorten service(edit,delete...)
 */
@Controller
public class PairController {
    @Resource
    UrlService urlService;


    @GetMapping("/home")
    public String toHome(){return "shorten";}

    @RequestMapping("/toList")
    public String toList() {
        return "url/list";
    }

    //store all the data of the table in the model to show it in list.html
    @RequestMapping("/list")
    public String list(Model model) {
        List<UrlPair> pairs = urlService.getPairList();
        model.addAttribute("pairs", pairs);
        return "url/list";
    }


    //method to deal with put service, the mapping between input shortUrl(in path) and longUrl(in body) will be edited
    // if the shortUrl you want to edit does not exist in database, return 404
    //else if the shortUrl is not valid, return 400
    @RequestMapping(value = "/g29",method = RequestMethod.PUT)
    public String toEdit(@RequestBody String longUrl, String shortUrl) {

        UrlPair urlPair = urlService.findByShort(shortUrl);
        if (urlPair == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your short url is not valid!");
        }
        urlPair.setLongUrl(longUrl);
        urlPair.setShortUrl(shortUrl);
        urlService.edit(urlPair);
        return "redirect:/list";
    }

    //method to deal with delete request
    //allow user to delete the mapping by input shortUrl
    //if success return 204, else if the shortUrl does not exist return 404
    @RequestMapping (value = "/g29",method = RequestMethod.DELETE)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(String shortUrl) {
        UrlPair urlPair = urlService.findByShort(shortUrl);
        if (urlPair == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your short url does not exist!");
        }
        Long id = urlPair.getId();
        urlService.deleteUrlById(id);
        return "redirect:/list";
    }

    //this method will pass the data of visit times to the frontend
    @RequestMapping("/view")
    public String view(Model model, Long id) {
        UrlPair urlPair = urlService.findUrlById(id);
        List<String> categories = new ArrayList<>();
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");
        categories.add("Sunday");

        List<Long> data = urlService.getViews(id);
        System.out.println("data=" + data.toString());
        System.out.println(data.toString());
        UrlPair urlPair1 = urlService.findUrlById(id);

        model.addAttribute("categories", categories);
        model.addAttribute("data", data);
        return "chart";
    }


}
