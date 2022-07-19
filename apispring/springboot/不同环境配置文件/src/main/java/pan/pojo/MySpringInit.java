package pan.pojo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author pan
 * @Date 2022/7/19 10:12
 * @Version 1.0
 * 我的初始化器
 * META-INF\spring.factories
 * 定义初始化器，应用比如过滤器
 */
public class MySpringInit implements ApplicationContextInitializer {
    //这里注册的Bean会优先会被加载
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerSingleton("u",new InitializerBean());
    }
}
