package com.pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 打包命令java -jar a.jar
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.pan.mapper"})  //1
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
