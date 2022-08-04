package pan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pan.MyConfig;

/**
 * @Author pan
 * @Date 2022/8/4 10:44
 * @Version 1.0
 */
@Configuration
public class MyGeConfig {
    @Bean
    public MyConfig myConfig(){
        return new MyConfig();
    }
}
