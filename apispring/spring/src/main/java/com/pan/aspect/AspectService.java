package com.pan.aspect;

/**
 * @Author pan
 * @Date 2022/8/4 11:23
 * @Version 1.0
 */
public class AspectService {
    public void foo(){
        System.out.println(123);
    }
    public static void main(String[] args) {
        //使用时需要安装一个插件，暂时没测出效果
        new AspectService().foo();
    }
}
