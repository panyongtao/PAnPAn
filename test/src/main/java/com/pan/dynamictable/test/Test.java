package com.pan.dynamictable.test;

import com.google.common.collect.Maps;
import com.pan.Application;
import com.pan.dynamictable.DynamicEntity;
import com.pan.dynamictable.DynamicService;
import com.pan.dynamictable.DynamicWrapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Test {
    @Autowired
    private  DynamicService dynamicService;
    @org.junit.Test
    public void test(){
        System.out.println(dynamicService.getById("tb_hero",1l));
    }

    /**
     * 保存参数有什么类型就保存什么字段
     */
    @org.junit.Test
    public void testsave(){
        DynamicEntity entity = new DynamicEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan");
        entity.setParams(map);
        entity.setId(0L);
        entity.setTableName("tb_hero");
        entity.setDateFormat(new SimpleDateFormat());
        dynamicService.save(entity);
    }

    /**
     * todo mysql和oracle如何确定表是否存在
     *
     */
    @org.junit.Test
    public void testExistTable(){
        dynamicService.existTable("tb_hero");
    }

    /**
     * 保存参数有什么类型就保存什么字段
     */
    @org.junit.Test
    public void testUpdate(){
        DynamicEntity entity = new DynamicEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan1");
        entity.setParams(map);
        entity.setId(12L);
        entity.setTableName("tb_hero");
        entity.setDateFormat(new SimpleDateFormat());
        dynamicService.updateById(entity);
    }

    @org.junit.Test
    public void testPage(){
        DynamicWrapper wrapper=new DynamicWrapper();
        wrapper.setTableName("tb_hero");
        dynamicService.list(wrapper);
    }

}
