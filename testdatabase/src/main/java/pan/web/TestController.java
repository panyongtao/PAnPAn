package pan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class TestController {
    @Autowired
    private DataSource dataSource;

    @RequestMapping("/hello")
    public String hello(){
        System.out.println(dataSource);
        return "hello spring boot123456789";
    }
}
