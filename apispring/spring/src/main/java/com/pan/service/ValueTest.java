package com.pan.service;

import com.pan.ImportConfig;
import com.pan.service.ano.LocalServerPort;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 14:38
 * @Version 1.0
 */
@Component
@Data
public class ValueTest {
    /**也可以注入属性，也可也注入一个bean类，也可以是一个spel表达式
     * @Value("${}") 可以获取对应属性文件中定义的属性值
     * */
    @Value("#{userService}")
    private UserService userService;

    @Value("${local.server.port}")
    private String port;
    @LocalServerPort
    private String intPort;
//    @Value("${}") 可以获取对应属性文件中定义的属性值
    public void test(){
        System.out.println(userService);
    }
    public void testport(){
        System.out.println(port);
    }
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        ValueTest bean = context.getBean(ValueTest.class);
        System.out.println(bean);
    }
}
