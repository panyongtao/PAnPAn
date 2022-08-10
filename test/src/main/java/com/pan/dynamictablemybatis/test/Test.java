package com.pan.dynamictablemybatis.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.pan.Application;
import com.pan.datasource.DynamicDataSource;
import com.pan.dynamictable.DynamicConfig;
import com.pan.dynamictable.DynamicWrapper;
import com.pan.dynamictablemybatis.DyEntity;
import com.pan.dynamictablemybatis.DyService;
import com.pan.dynamictablemybatis.DyWrapper;
import com.pan.mapper.DyMapper;
import lombok.SneakyThrows;
import org.junit.runner.RunWith;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Test {
    @Autowired
    private DyService dyService;
    @Autowired
    private DyService dynamicService2;
    @Autowired
    private DyMapper dynamicMapper;
    @org.junit.Test
    public void test(){
        System.out.println(dyService.getById("tb_hero",1l));
    }

    @Autowired
    private DynamicConfig dynamicConfig;
    @org.junit.Test
    public void testd(){
        System.out.println(dynamicConfig);
    }
    /**
     * 保存参数有什么类型就保存什么字段
     */
    @org.junit.Test
    public void testsave(){
        DyEntity entity = new DyEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan");
        entity.setParams(map);
        entity.setId(0L);
        entity.setTableName("tb_hero");
        entity.setDateFormat(new SimpleDateFormat());
        dyService.save(entity);
    }
    /**需要关闭主键自动生成策略*/
    @org.junit.Test
    public void testOracleSave(){
        DynamicDataSource.datasourceName.set("Oracle");
        DyEntity entity = new DyEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan12");
        Long seq_tb_hero = dyService.selectSeq("seq_tb_hero");
        entity.setId(seq_tb_hero);
        entity.setTableName("tb_hero");
        entity.setParams(map);
        dyService.save(entity);
//        System.out.println(dynamicService.querySql("select seq_tb_hero.nextval from dual"));
        //System.out.println(dynamicService.selectSeq("seq_tb_hero"));
    }

    /**
     * 检查表是否存在
     */
    @org.junit.Test
    public void testExistTable(){
        dyService.existTable("tb_hero");
    }

    @org.junit.Test
    public void testTableColumn(){
        DynamicDataSource.datasourceName.set("Oracle");
        dyService.selectColumns("tb_hero");
    }

    /**
     * 保存参数有什么类型就保存什么字段
     */
    @org.junit.Test
    public void testUpdate(){
        DyEntity entity = new DyEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan1");
        entity.setParams(map);
        entity.setId(12L);
        entity.setTableName("tb_hero");
        entity.setDateFormat(new SimpleDateFormat());
        dyService.updateById(entity);
    }

    /**
     * 列表查询或者分页查询
     */
    @org.junit.Test
    public void testPage(){
        DynamicDataSource.datasourceName.set("Mysql");
        System.out.println(DynamicDataSource.getDatasourceType());
        DyWrapper wrapper=new DyWrapper();
        wrapper.setTableName("tb_hero");
        wrapper.setCurrent(2);
        wrapper.setSize(5);
        IPage page= new Page();
        page.setTotal(dyService.count(wrapper));
        Long pages;
        if(page.getTotal()%page.getSize()==0){
            pages=page.getTotal()/page.getSize();
        }else{
            pages=page.getTotal()/page.getSize()+1;
        }
        page.setPages(pages);

        page.setRecords(dyService.list(wrapper));
        System.out.println(R.data(page));
    }

    /**
     * 列表查询或者分页查询
     */
    @org.junit.Test
    public void testList(){
        DyWrapper wrapper=new DyWrapper();
        wrapper.setTableName("tb_hero");
        System.out.println(dyService.list(wrapper));
    }
    /**
     * 列表查询或者分页查询
     */
    @org.junit.Test
    public void testList1(){
        DyWrapper wrapper=new DyWrapper();
        wrapper.setTableName("cable");
        System.out.println(dyService.list(wrapper));
    }

    /**
     * 列表查询或者分页查询
     */
    @org.junit.Test
    public void testList2(){
        DyWrapper wrapper=new DyWrapper();
        wrapper.setTableName("cable");
        System.out.println(dyService.list(wrapper));
    }


    /**
     * 通用sql查询
     */
    @org.junit.Test
    public void testSql(){
        dyService.querySql("select * from tb_hero");
    }
    /**
     * 查询数量
     */
    @org.junit.Test
    public void testCount(){
        DynamicWrapper wrapper=new DynamicWrapper();
        wrapper.setTableName("tb_hero");
        DynamicDataSource.datasourceName.set("Oracle");
//        dynamicService.count(wrapper);
    }
    @org.junit.Test
    @SneakyThrows
    public void testT(){
        Class<? extends DyService> clazz = dynamicService2.getClass();
        Method f = clazz.getMethod("f");
        f.invoke(dynamicService2);
    }
}