package pan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("apispring/apiweb/test")
    public String test(){
        System.out.println(123);
        return "你好";
    }
    @GetMapping("apispring/apiweb/login")
    public String login(){
        return "login";
    }
}
