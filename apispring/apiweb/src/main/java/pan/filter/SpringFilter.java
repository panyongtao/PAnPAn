package pan.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤器使用1.@WebFilter注解 2.@ServletComponentScan包扫描
 */
@Component
public class SpringFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我使用spring注解bean过滤了数据");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
