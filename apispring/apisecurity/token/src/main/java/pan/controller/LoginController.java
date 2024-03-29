package pan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pan.domain.ResponseResult;
import pan.domain.User;
import pan.service.LoginServcie;

@RestController
public class LoginController {

    @Autowired
    private LoginServcie loginServcie;

    private Authentication getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication. getPrincipal();
        return authentication;
    }

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        //登录
        return loginServcie.login(user);
    }

    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginServcie.logout();
    }
}
