package com.pan.test;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pan.Application;
import com.pan.mapper.MybatisHeroMapper;
import com.pan.pojo.MybatisHero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestHero {

    @Autowired
    MybatisHeroMapper mapper;

    @Test
    public void test(){
        System.out.println(new MybatisHero().selectAll());
    }
    @Test
    public void test1(){
        LambdaQueryWrapper<MybatisHero> wrapper = new LambdaQueryWrapper<>();
        System.out.println(mapper.selectList(wrapper));
    }
}
