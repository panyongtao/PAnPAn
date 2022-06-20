package com.fmjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/*import org.mybatis.spring.annotation.MapperScan;*/


@SpringBootApplication
@ImportResource({"classpath:consumer.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
