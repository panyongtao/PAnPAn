package pan.springmvcconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pan.interceptor.MyFirstInterceptor;

/**
 * @Author pan
 * @Date 2022/7/16 14:06
 * @Version 1.0
 * springboot 2.0以前
 * extends WebMvcConfigurerAdapter
 */
@Configuration//定义此类为配置类
public class InterceptorConfig implements WebMvcConfigurer {
    //设置首页跳转路径 此方法可以很方便的实现一个请求到视图的映射，而无需书写controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        System.out.println(123);
        registry.addViewController("/").setViewName( "redirect:/apispring/apiweb/login" );
    }

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

