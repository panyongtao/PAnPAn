package com.pan;

import com.pan.service.*;
import com.pan.service.ano.MyComponent;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.*;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * FilterType 可以是注解也可以是接口，切面
 */
@ComponentScan(value = "com.pan",
        includeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION,value=MyComponent.class))
//需要导入资源才可以用，大都督那里的这里的就可以直接用
@PropertySource("spring.properties")
@Configuration
public class CommonConfig {
    /**autowireCandidate=false标识此bean不能被其他bean导入使用*/
    @Bean(autowire = Autowire.BY_NAME)
    public NotNeedWiredUserService notNeedWiredUserService(){
        return new NotNeedWiredUserService();
    }
    @com.pan.service.ano.PrototypeBean
    public PrototypeBean prototypeBean(){
        return new PrototypeBean();
    }
    /**测试不用@Configuration的影响，此时Bean之间调用的方法
     * 会变成原生模式（lite模式）
     * 使用@Configuration注解就会解决这些问题
     * proxyBeanMethods=false 也是原生模式
     * 标识bean方法不是一个代理方法
     * */
    @Bean
    public User user(){
        return new User();
    }
    @Bean
    public Order order(){
        System.out.println(user());
        System.out.println(user());
        return new Order();
    }
    @Bean
    public ProxyFactoryBean userServiceProxy() {
        UserService userService = new UserService();
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userService);
        proxyFactoryBean.addAdvice(new MethodInterceptor(){
            @Override
            public Object invoke( MethodInvocation invocation) throws Throwable {
                System.out.println("构造代理bean前");
                Object proceed = invocation.proceed();
                System.out.println("构造代理bean后");
                return proceed;
            }
        });
        return proxyFactoryBean;
    }

    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(CommonConfig.class);
        Order bean = context.getBean(Order.class);
        System.out.println(bean);

        //测试使用ImportUser包扫描，导入的Bean实际上是一个配置Bean
        OrderService bean1 = context.getBean(OrderService.class);
        System.out.println(bean1);
        UserService userServiceProxy = (UserService) context.getBean("userServiceProxy");
        userServiceProxy.test();
    }
}
