package pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 用户名user 密码 启动时会提供Using generated security password:
 * 认证可以分为token认证和会话认证，token认证比较安全，而且具有平台无关性
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(run);
    }
}
