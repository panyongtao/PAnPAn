package com.pan.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author pan
 * @Date 2022/7/22 9:47
 * @Version 1.0
 * !!只能通过浏览器才能进行测试出来
 */
@RestController
@Data
public class Mvc {
    @GetMapping("mvc/test")
    public String test(){
        return "pan";
    }
    @GetMapping("mvc/set")
    public String test1(HttpSession session){
        session.setAttribute("pan","潘");
        return "pan1";
    }
    @GetMapping("mvc/get")
    public String test2(HttpSession session){
        return (String) session.getAttribute("pan");
    }
}
