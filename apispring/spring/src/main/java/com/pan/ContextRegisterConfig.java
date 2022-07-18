package com.pan;

import com.pan.service.Order;
import com.pan.service.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Supplier;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * FilterType 可以是注解也可以是接口，切面
 */
public class ContextRegisterConfig {

    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ContextRegisterConfig.class);
        context.register(User.class);
        User bean = context.getBean(User.class);
        System.out.println(bean);
        context.registerBean("order", Order.class, new Supplier<Order>() {
            @Override
            public Order get() {
                return new Order();
            }
        });
        Order bean1 = (Order) context.getBean("order");
        System.out.println(bean1);
    }
}
