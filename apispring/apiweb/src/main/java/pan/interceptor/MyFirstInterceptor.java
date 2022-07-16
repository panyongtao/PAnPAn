package pan.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * springboot中使用拦截器1.定义拦截器 2.WebMvcConfig添加拦截器规则
 * 所有的过滤器执行完之后，才会执行拦截器
 */
public class MyFirstInterceptor implements HandlerInterceptor {
    @Override
    //当处理器方法执行之前调用
    //返回值: true  放行   false  不放行    就执行不了处理器方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle当处理器方法执行之前调用1");
        return true;
    }

    @Override
    //当处理器方法执行之后会自动调用调用
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle当处理器方法执行之后会自动调用调用1");
    }

    @Override
    //请求处理完毕且视图渲染之后, 会调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion请求处理完毕之后,会调用1");
    }
}
