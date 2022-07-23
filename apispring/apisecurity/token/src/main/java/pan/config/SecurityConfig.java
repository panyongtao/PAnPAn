package pan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pan.filter.JwtAuthenticationTokenFilter;

@Configuration
/**权限启用-1.启用权限注解,
 * 也可以用@EnableWebSecurity注解替代
 * 此注解相当于@Configuration，@Import,@EnableGlobalMethodSecurity
 * springboot中不用加这一个注解 @EnableWebSecurity
 * 默认登录的界面DefaultLoginPageGeneratingFilter是由这个过滤器产生的
 * 退出页面默认 /logout
 *
 * 黑马认证的原理是直接加载用户，登录成功就会调整，认证采用自动认证
 *
 * securedEnabled
 * */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**创建BCryptPasswordEncoder注入容器，设置密码的加密方式，
     * 如果想使用明文的方式数据应该存储成{noop}1234这种形式
     * 下面的那个bean就可以不用写
    */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //异常处理
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        指定默认的登录界面，对于浏览器的请求如果没有权限通过默认是会调转到这个页面
//        http.formLogin().loginPage("/login.html");
//        http.formLogin().loginProcessingUrl() 登录之后要处理的url
//        http.formLogin().successForwardUrl("success.html") 成功登录时的页面
//          http.formLogin().failureForwardUrl("失败时的页面")
//        http.logout().logoutUrl().logoutSuccessUrl() 推出逻辑

        http
                //关闭csrf，关闭跨站请求比如U登录了A网站，这时候B网站有A网站的链接是不允许操作的
//会屏蔽除get之外的大部分的请求方法
                .csrf().disable()

                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问，UsernamePasswordAuthenticationFilter此时就不会生效
                // 因为没有携带用户名和密码信息而且不是使用表单认证此时无法认证所以需要此接口的手动认证
//                认证完之后二次登录信息时，需要定义网关
                .antMatchers("/user/login").anonymous()
//                .antMatchers("/testCors").hasAuthority("system:dept:list222") 资源权限配置
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
//        http.authorizeRequests().anyRequest().permitAll(); 允许所有的请求
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //配置异常处理器
        http.exceptionHandling()
                //配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
