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
import org.junit.runner.RunWith;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class Test {
    @Autowired
    private DyService dynamicService;
    @Autowired
    private DyMapper dynamicMapper;
    @org.junit.Test
    public void test(){
        System.out.println(dynamicService.getById("tb_hero",1l));
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
        dynamicService.save(entity);
    }
    /**需要关闭主键自动生成策略*/
    @org.junit.Test
    public void testOracleSave(){
        DynamicDataSource.datasourceName.set("Oracle");
        DyEntity entity = new DyEntity();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("username","panpan12");
        Long seq_tb_hero = dynamicService.selectSeq("seq_tb_hero");
        entity.setId(seq_tb_hero);
        entity.setTableName("tb_hero");
        entity.setParams(map);
        dynamicService.save(entity);
//        System.out.println(dynamicService.querySql("select seq_tb_hero.nextval from dual"));
        //System.out.println(dynamicService.selectSeq("seq_tb_hero"));
    }

    /**
     * 检查表是否存在
     */
    @org.junit.Test
    public void testExistTable(){
        dynamicService.existTable("tb_hero");
    }

    @org.junit.Test
    public void testTableColumn(){
        DynamicDataSource.datasourceName.set("Oracle");
        dynamicService.selectColumns("tb_hero");
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
        dynamicService.updateById(entity);
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
        page.setTotal(dynamicService.count(wrapper));
        Long pages;
        if(page.getTotal()%page.getSize()==0){
            pages=page.getTotal()/page.getSize();
        }else{
            pages=page.getTotal()/page.getSize()+1;
        }
        page.setPages(pages);

        page.setRecords(dynamicService.list(wrapper));
        System.out.println(R.data(page));
    }

    /**
     * 列表查询或者分页查询
     */
    @org.junit.Test
    public void testList(){
        DyWrapper wrapper=new DyWrapper();
        wrapper.setTableName("tb_hero");
        System.out.println(dynamicService.list(wrapper));
    }


    /**
     * 通用sql查询
     */
    @org.junit.Test
    public void testSql(){
        dynamicService.querySql("select * from tb_hero");
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
}