//package com.pan.datasource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Primary
//public class DynamicDataSource extends AbstractRoutingDataSource {
//    @Autowired
//    DataSource mysqlDatasource;
//    @Autowired
//    DataSource oracleDatasource;
//    //当前使用的数据源标识
//    public static ThreadLocal<String> datasourceName=new ThreadLocal();
//    /**
//     *返回当前数据源标识
//     */
//    @Override
//    protected Object determineCurrentLookupKey() {
//        System.out.println("本地线程共享变量:"+datasourceName.get());
//        return datasourceName.get();
//    }
//    @Override
//    public void afterPropertiesSet() {
//        //为targetDataSource初始化所有数据源
//        //为defaultTargetDataSource设置默认的数据源
//        Map<Object,Object> targetSource=new HashMap();
//        targetSource.put("Mysql",mysqlDatasource);
//        targetSource.put("Oracle",oracleDatasource);
//        super.setTargetDataSources(targetSource);
//        super.setDefaultTargetDataSource(mysqlDatasource);
//        System.out.println("所有数据源");
//        super.afterPropertiesSet();
//    }
//}
