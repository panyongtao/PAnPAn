package com.pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.pan.dao","com.pan.mapper"})  //1
@EnableScheduling
@ImportResource(locations = {"classpath:thread-pool.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
//    @EnableOAuth2Sso
//    @Component
//    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Resource
//        private McFilterSecurityInterceptor mcFilterSecurityInterceptor ;
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.headers().frameOptions().disable ();
//            http.addFilterBefore(mcFilterSecurityInterceptor,FilterSecurityInterceptor.class);
//            http. authorizeRequests(). antMatchers(....antPatterns: " /services/v1/*”).permitAll().
//            antMatchers( ....antPatterns: "/**") . authenticated() ;
//            http. authorizeRequests(). antMatchers("/4NK"").permitAll();
//        }
//
//    }
}
