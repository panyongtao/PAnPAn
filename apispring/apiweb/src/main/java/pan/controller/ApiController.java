package pan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("apispring/apiweb/test")
    public String test(){
        return "你好";
    }
}
