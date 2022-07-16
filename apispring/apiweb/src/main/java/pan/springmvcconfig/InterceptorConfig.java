package pan.springmvcconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pan.interceptor.MyFirstInterceptor;

/**
 * @Author pan
 * @Date 2022/7/16 14:06
 * @Version 1.0
 */
@Configuration//定义此类为配置类
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns拦截的路径
        String[] addPathPatterns = {
                "/user/**","/apispring/apiweb/test"
        };
        //excludePathPatterns排除的路径
        String[] excludePathPatterns = {
                "/user/login","/user/noLg","/user/error"
        };
        //创建用户拦截器对象并指定其拦截的路径和排除的路径
        registry.addInterceptor(new MyFirstInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}

