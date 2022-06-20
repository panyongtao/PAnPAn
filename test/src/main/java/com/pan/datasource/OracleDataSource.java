package com.pan.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Throwables;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
@Slf4j
public class OracleDataSource {
//    @Value("jdbc:oracle:thin:@//localhost:1521/orcl")
    @Value("${spring.datasource.url}")
    private String url;
//    @Value("SCOTT")
    @Value("root")
    private String username ;
    @Value("pan1234")
    private String password;
//    @Value("oracle.jdbc.driver.OracleDriver")
@Value("${spring.datasource.driver-class-name}")
    private String driverClassName ;
    @Value("com.pan.mapper")
    private String typeAliasesPackage ;
    @Value("classpath*:mapper/*.xml")
    private String mapperLocations;

    @Value("200")
    private int maxActive;
    @Value("1")
    private int minIdle;
    @Value("30")
    private int queryTimeout;
    @Value("30")
    private int loginTimeout;
    @Value("50")
    private int initialsize;
    @Value("config")
    private String filters;
    @Value ("60000")
    private int maxWait;
    @Value("${mybatis.configuration.log-impl:}")
    private String logClassStr;
    @Bean (name = "oracleDatasource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        return createDataSource (url,username,password, driverClassName);
    }
    @SneakyThrows
    private DruidDataSource createDataSource(String url,String username,String password,String driverClassName) {
        if (StringUtils.isEmpty(url) || StringUtils.isBlank(username) || StringUtils.isBlank(driverClassName)) {
            log.error("数据库配置异常,请检查参数配置");
            throw new Exception("数据库配置信息异常");
        }
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialsize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setQueryTimeout(queryTimeout);
        datasource.setLoginTimeout(loginTimeout);
        datasource.setTimeBetweenEvictionRunsMillis(60000);
        datasource.setMinEvictableIdleTimeMillis(3600000);
        datasource.setValidationQuery("SELECT 1 FROM DUAL");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);

        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid conf iguration initialization filter error",
                    Throwables.getStackTraceAsString(e));
        }
        return datasource;
    }
 }
