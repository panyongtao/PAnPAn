package com.pan.controller;

import com.pan.response.BaseResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * (TbHero)表控制层
 *
 * @author makejava
 * @since 2022-03-27 14:25:59
 */
@RestController
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<T>> doException(Exception e, HttpServletResponse response){
        System.out.println("捕获异常");
            return new ResponseEntity<BaseResponse<T>>(new BaseResponse(),HttpStatus.OK);
    }

}