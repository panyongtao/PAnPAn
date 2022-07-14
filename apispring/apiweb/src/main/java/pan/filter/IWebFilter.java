package pan.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 过滤器使用1.@WebFilter注解 2.@ServletComponentScan包扫描
 */
@WebFilter
public class IWebFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我过滤了数据");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
