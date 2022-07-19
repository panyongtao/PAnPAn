package com.pan;

import com.pan.service.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * FilterType 可以是注解也可以是接口，切面
 */
@PropertySource("spring.properties")
@Import(ImportUser.class)//导入的bean其实是一个配置bean，会在Bean靠后的位置导入bean
public class ImportConfig {
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
    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        Order bean = context.getBean(Order.class);
        System.out.println(bean);

        //测试使用ImportUser包扫描，导入的Bean实际上是一个配置Bean
        OrderService bean1 = context.getBean(OrderService.class);
        System.out.println(bean1);
    }
}
