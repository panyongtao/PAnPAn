package com.pan.test;


import com.pan.Application;
import com.pan.dao.TbHeroDao;
import com.pan.entity.TbHero;
import com.pan.mapper.HeroMapper;
import com.pan.mapper.HeroTkMapper;
import com.pan.mapper.MybatisHeroMapper;
import com.pan.pojo.MybatisHero;
import com.pan.pojo.TkHero;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestHero {

    @Autowired
    HeroMapper mapper;
    @Autowired
    HeroTkMapper tkMapper;
    @Autowired
    MybatisHeroMapper mybatisHeroMapper;
    @Autowired
    DataSource ds;
    @Resource
    private TbHeroDao tbHeroDao;

    /**
     * mybatis
     */
    @Test
    public void test7(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        System.out.println(tbHeroDao.queryJoinCables(list));
    }

    /**
     * mybatis
     */
    @Test
    public void test6(){
        System.out.println(tbHeroDao.queryJoinCable(2));
    }

    @Test
    public void test61(){
        System.out.println(tbHeroDao.queryJoinCable1(2));
    }
    @Test
    public void test62(){
        System.out.println(tbHeroDao.querySql("select t.*,c.cable_name,c.hero_id\n" +
                "        from tb_hero t\n" +
                "        left join cable c on t.id=c.hero_id"));
    }

    /**
     * mybatis
     */
    @Test
    public void test5(){
        System.out.println(tbHeroDao.queryById(1));
    }

    /**
     * mybatis
     */
    @Test
    public void test4(){
        System.out.println(tbHeroDao.queryAll(new TbHero()));
    }

    /**
     * tk
     */
    @Test
    public void test(){
        System.out.println(new TkHero().selectAll());
    }
    @Test
    public void selectTkOne() {
        TkHero tkHero = new TkHero();
        tkHero.setId(1);
        TkHero hero = tkHero.selectByField(TkHero::getId);
        System.out.println(hero);
    }
    /**
     * 普通通用mapper
     */
    @Test
    public void test1(){
        System.out.println(mapper.selectAll());
    }

    /**
     * mybatis
     */
    @Test
    public void test2(){
        System.out.println(new MybatisHero().selectAll());
    }
}
