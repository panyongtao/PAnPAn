package pan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {
    @GetMapping("apispring/apiweb/test")
    public String test(){
        System.out.println(123);

        return "你好1";
    }

    @Autowired
    RestTemplate restTemplate;
    @GetMapping("apispring/apiweb/template")
    public String test2(){
        System.out.println(123);
//        restTemplate.getForObject()
        return "你好1";
    }

    @GetMapping("apispring/apiweb/login")
    public String login(){
        return "login";
    }
}
