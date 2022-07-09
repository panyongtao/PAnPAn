package pan.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.sgl.mystarter.sglhello.config.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pan.Application;
import pan.config.PropertiesMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class TestDatabase {
    @Autowired
    private PropertiesMap propertiesMap;
    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private HelloService helloService;
    @Test
    public void test(){
        System.out.println(dataSource);
        System.out.println(propertiesMap);
    }
    @Test
    public void test1(){
        System.out.println(helloService);
    }



}
