package com.pan.test;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ejlchina.searcher.BeanSearcher;
import com.ejlchina.searcher.util.MapUtils;
import com.github.pagehelper.PageHelper;
import com.pan.Application;
import com.pan.dao.TbHeroDao;
import com.pan.datasource.DynamicDataSource;
import com.pan.entity.TbHero;
import com.pan.mapper.HeroMapper;
import com.pan.mapper.HeroTkMapper;
import com.pan.mapper.MybatisHeroMapper;
import com.pan.pojo.MybatisHero;
import com.pan.pojo.TbOracleHero;
import com.pan.pojo.TkHero;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;

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
    @Autowired
    SqlSession sqlSession;
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Resource
    private TbHeroDao tbHeroDao;
    @Autowired
    private BeanSearcher beanSearcher;
    @Autowired
    private BeanSearcher mapSearcher;

    /**
     * 测试BeanSearcher
     */
    @Test
    public void test71(){
//        DynamicDataSource.datasourceName.set("Oracle");
        TkHero tkHero = new TkHero();
        //System.out.println(beanSearcher.searchAll(TkHero.class,new HashMap<>()));
        //System.out.println(tkHero.selectAll());
        com.pan.pojo.TbHero  tbHero = new com.pan.pojo.TbHero ();
        Map param=MapUtils.builder()
                .field(com.pan.pojo.TbHero::getCableName, "H").build();
        tbHero.setParams(param);
        //System.out.println(mapSearcher.searchAll(com.pan.pojo.TbHero.class, new HashMap<>()));
        System.out.println(tbHero.searchAll());
    }

    @Test
    public void test72(){
        com.pan.pojo.TbHero  tbHero = new com.pan.pojo.TbHero();
        System.out.println(tbHero.searchAll());
    }

    /**
     * 数据源的使用
     */
    @Test
    public void test73(){
        DynamicDataSource.datasourceName.set("Oracle");
        System.out.println(new TbOracleHero().searchAll());
    }

    /**
     * mysql分页或者oracle分页测试
     */
    @Test
    public void test74(){
        DynamicDataSource.datasourceName.set("Oracle");
        PageHelper.startPage(1,4);
        System.out.println(new TkHero().selectAll());
    }

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
     * 总结以下的执行过程tbHeroDao实际上是MapperProxy这个代理类把当前接口赋给了此代理类的一个接口属性
     * 之后调用下面这个invoke方法，其中method就是querySql2,sqlSession是由会话工厂创建的
     * 1.invoke(proxy, method, args, sqlSession);
     * 2.mapperMethod.execute(sqlSession, args);，这一步就是我们常见的增删改查方法
     */
    @Test
    public void test63(){
        tbHeroDao.querySql2();
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
//        System.out.println(beanSearcher.search(MybatisHero.class, new HashMap<>()));
        //这里是有问题的，除非把动态数据源去掉
        LambdaQueryWrapper<MybatisHero> wrapper=new LambdaQueryWrapper();
        wrapper.eq(MybatisHero::getId,"1");
        System.out.println(mybatisHeroMapper.selectList(wrapper));
//        System.out.println(new MybatisHero().selectAll());
    }
    /**
     * mybatis
     */
    @Test
    public void test211(){
        LambdaQueryWrapper<MybatisHero> wrapper=new LambdaQueryWrapper();
        wrapper.eq(MybatisHero::getId,"1");
//        System.out.println(new MybatisHero().selectList(wrapper));
    }
    /**
     * 测试动态数据源事务回滚,必须开启事务注解才会生效
     */
    @Test
    @Transactional
    public void save(){
        TbHero tbHero = new TbHero();
        tbHero.setId(100);
        tbHero.setUsername("pantest");
        tbHeroDao.insert(tbHero);
        System.out.println(1/0);
    }
}
