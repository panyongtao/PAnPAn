package com.example.test;

import com.ejlchina.searcher.BeanSearcher;
import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.operator.*;
import com.ejlchina.searcher.param.Operator;
import com.ejlchina.searcher.util.MapUtils;
import com.example.Application;
import com.example.sbean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestBean {
    @Resource
    private BeanSearcher beanSearcher;
    @Resource
    private MapSearcher mapSearcher;
    /*查询所有*/
    @Test
    public void test(){
//        System.out.println(new TbHero().searchAll());
        TbHeroExtend tbHeroExtend = new TbHeroExtend();
        List<TbHeroExtend> tbHeroExtends = beanSearcher.searchAll(TbHeroExtend.class, new HashMap<>());
        System.out.println(tbHeroExtend.searchAll());
    }
    /*动态域*/
    @Test
    public void test2(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fieldName","username");
        SearchResult<Map<String, Object>> search = mapSearcher.search(Tmp.class, hashMap);
        System.out.println(search);
    }
    /*数字范围*/
    @Test
    public void test3(){
        Map<String, Object> hashMap = MapUtils.builder()
                .field("id",1,5).op(Operator.Between).build();
        SearchResult<Map<String, Object>> search = mapSearcher.search(TbHero.class, hashMap);
        System.out.println(search);
    }

    /*测试参数为null或者为空*/
    @Test
    public void test311(){
//        Map<String, Object> hashMap = MapUtils.builder()
//                .field("id").op(Operator.Equal).build();
        String id = null;
//        Map<String, Object> hashMap = MapUtils.builder()
//                .field("id",id).op(Operator.Equal).build();
        Map<String, Object> hashMap = MapUtils.builder()
                .field("id",1).op(Operator.Contain).build();
        SearchResult<Map<String, Object>> search = mapSearcher.search(TbHero.class, hashMap);
        System.out.println(search);
    }

    /*日期范围 时间：yyyy-MM-dd HH:mm*/
    @Test
    public void test4(){
        Map<String, Object> hashMap = MapUtils.builder()
                .field(TbHero::getOnlinetime,"2020-10-21","2020-10-24").op(Operator.Between).build();
        SearchResult<Map<String, Object>> search = mapSearcher.search(TbHero.class, hashMap);
        System.out.println(search.getDataList());
    }
    /*数字范围*/
    @Test
    public void test31(){
        Map<String, Object> hashMap = MapUtils.builder()
                .build();
        SearchResult<Map<String, Object>> search = mapSearcher.search(TbHeroMax.class, hashMap);
        System.out.println(search);
    }
    /*动态条件sql*/
    @Test
    public void test41(){
        Map<String, Object> hashMap = MapUtils.builder()
                .field(TbHero::getUsername).sql("$1='阿木'").build();
        SearchResult<Map<String, Object>> search = mapSearcher.search(TbHero.class, hashMap);
        System.out.println(search.getDataList());
    }
    /*只查询第一个*/
    @Test
    public void test5(){
        Map<String, Object> hashMap = MapUtils.builder()
                .field(TbHero::getOnlinetime,"2020-10-21","2020-10-24").op(Operator.Between).build();
        System.out.println(mapSearcher.searchFirst(TbHero.class, hashMap));
    }
    /*统计*/
    @Test
    public void test6(){
        Map<String, Object> hashMap = MapUtils.builder()
                .field(TbHero::getOnlinetime,"2020-10-20","2020-10-24").op(Operator.Between).build();
        SearchResult<TbHero> search = beanSearcher.search(TbHero.class, hashMap, new String[]{"id"});
        System.out.println(search.getDataList());
        System.out.println(search.getSummaries()[0].intValue());
    }
    /*动态指定查询的表名*/
    @Test
    public void dynamicTable() {
        Map<String, Object> map = new HashMap<>();
        map.put("table", "department");
        System.out.println(mapSearcher.searchList(DTableBean.class, map));
    }
    /*只查询name*/
    @Test
    public void dynamicTable1() {
        Map<String, Object> map = MapUtils.builder()
                .onlySelect(DTableBean::getName).build();
        map.put("table", "department");
        System.out.println(mapSearcher.searchList(DTableBean.class, map));
    }
    /**
     * 后端参数写法
     */
    public void test1(){
        Map<String, Object> params = MapUtils.builder()
                .page(0, 15)                    // 第 0 页，每页 15 条（推荐写法）
                .put("page", 0)                 // 等效写法
                .put("size", 15)                // 等效写法
                .build();
        params = MapUtils.builder()
                .orderBy(User::getAge, "desc")              // 等效写法 1
                .put("sort", "age")                         // 等效写法 2
                .put("order", "desc")
                // 等效写法 2
                .build();
        params = MapUtils.builder()
                .put("orderBy", "age:asc,time:desc")        // 等效写法
                .build();
         params = MapUtils.builder()
                .field("name", "Jack")          // (1) 字段 name 的值为 Jack
                .field(User::getName, "Jack")   // 等效写法 == (1)
                .op("eq")                       // (2) 指定 name 字段的运算符为 eq (默认就是 eq, 所以也可以省略)
                .op("Equal")                    // 等效写法 == (2)
                .op(Operator.Equal)             // 等效写法 == (2)
                .op(Equal.class)                // 等效写法 == (2)
                .build();
         //逻辑分组
        params = MapUtils.builder()
                .group("A")             // A 组开始
                .field(User::getName, "Jack").ic()
                .field(User::getGender, "Male")
                .group("B")             // B 组开始
                .field(User::getName, "Alice")
                .field(User::getGender, "Female")
                .group("C")             // C 组开始
                .field(User::getAge, "20").op(GreaterEqual.class)
                .groupExpr("(A|B)&C")   // 组间逻辑关系
                .build();
        params = MapUtils.builder(params).build();  // 在原有参数基础之上可以进行构建参数
        //模糊查询
         params = MapUtils.builder()
                .field(User::getName, "A").op(Contain.class)    // 包含 A
                .field(User::getName, "A").op(StartWith.class)  // 以 A 开头
                .field(User::getName, "A").op(EndWith.class)    // 以 A 结尾
                .field(User::getName, "A%", "%B", "%C%").op(OrLike.class)  // 以 A 开头 或 以 B 结尾 或 包含 C
                .build();
        //多值查询
        params = MapUtils.builder()
                .field(User::getId, 1, 2, 3).op(InList.class)
                .build();
        //非空查询
        params = MapUtils.builder()
                .field(User::getName).op(NotEmpty.class)
                .build();
        //多字段动态sql使用
        params = MapUtils.builder()
                // 生成 SQL 条件：id < 100 or age > 10
                .field(User::getId, User::getAge).sql("$1 < 100 or $2 > 10")
                .build();
        //多字段占位符
        params = MapUtils.builder()
                // 生成 SQL 条件：id < ? or age > ?，两个占位符参数分别为：100，10
                .field(User::getId, User::getAge).sql("$1 < ? or $2 > ?", 100, 10)
                .build();
    }
}
