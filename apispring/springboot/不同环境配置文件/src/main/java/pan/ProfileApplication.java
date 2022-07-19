package pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pan.pojo.ProfileBean;

@SpringBootApplication
public class ProfileApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProfileApplication.class, args);
        System.out.println(run.getBean(ProfileBean.class));
    }
}
