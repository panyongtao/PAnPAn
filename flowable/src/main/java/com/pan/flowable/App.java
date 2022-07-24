package com.pan.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author pan
 * @Date 2022/7/24 13:31
 * @Version 1.0
 * 访问
 * http://localhost:8080/flowable-ui
 * amin test
 * 当然配置文件也可以写在默认配置文件application.yml
 *
 * 由于依赖的冲突需要单独启应用 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.flowable.ui.application"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
