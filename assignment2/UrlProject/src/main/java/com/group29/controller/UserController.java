package com.group29.controller;

import com.group29.bean.User;
import com.group29.jwt.JWTUtils;
import com.group29.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ryyyyyy
 * @create 2023-04-18 3:23
 */
@Controller
public class UserController {

    private static final long EXPIRE_TIME = 604800;


    @Resource
    UserService userService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(@RequestBody User user){

        Map map = new HashMap<String,Object>();
        String userName = user.getName();
        String pwd = user.getPwd();
        try {
            User userDB = userService.login(userName, pwd);
            long currentTime = System.currentTimeMillis();
            Map<String,Object> payLoadMap = new HashMap<String,Object>();
            payLoadMap.put("id",userDB.getId());
            payLoadMap.put("name", userDB.getName());
            payLoadMap.put("iat",currentTime);
            payLoadMap.put("exp",currentTime + EXPIRE_TIME);
            String token = JWTUtils.getToken(payLoadMap);
            map.put("state",true);
            map.put("msg","login successfully!");
            map.put("token",token);
        } catch (Exception e) {
            map.put("state",false);
            map.put("msg","fail to login!");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "fail to login!");
        }
        return map;
    }

    @PostMapping("/user")
    @ResponseBody
    public Map<String,Object> register(@RequestBody User user){
        Map map = new HashMap<String,Object>();
        String userName = user.getName();
        String pwd = user.getPwd();
        try {
            User userDB = userService.register(userName, pwd);
            map.put("state",true);
            map.put("msg","register successfully!");
        } catch (Exception e) {
            map.put("state",false);
            map.put("msg","duplicate user!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "fail to login!");
        }
        return map;
    }

    @PutMapping("/user")
    @ResponseBody
    public Map<String,Object> edit(@RequestBody Map<String, Object> userData){

        Map map = new HashMap<String,Object>();
        String name = (String) userData.get("name");
        String oldPwd = (String) userData.get("oldPwd");
        String newPwd = (String) userData.get("newPwd");

        try {
            User userDB = userService.login(name,oldPwd);
            userDB.setPwd(newPwd);
            userService.edit(userDB);
            map.put("status",true);
            map.put("msg","update successfully!");
        } catch (Exception e) {
            map.put("status",false);
            map.put("msg","wrong old password!");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "wrong old password!");
        }
        return map;
    }
}
