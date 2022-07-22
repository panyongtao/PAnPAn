package com.pan.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author pan
 * @Date 2022/7/22 9:47
 * @Version 1.0
 * 服务器会根据cookie jsessionid来去找session
 * request.getSession(),如果没有就新建一个session
 */
@RestController
@Data
public class MvcSession {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("mvc/set1")
    public String test1(@CookieValue("JSESSIONID") String sessionid){
        session.setAttribute("pan","潘");
        return "pan1";
    }
    @GetMapping("mvc/get1")
    public String test2(){
        return (String) session.getAttribute("pan");
    }
}
