package pan.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pan.domain.ResponseResult;

@RestController
public class HelloController {
    @RequestMapping("/hello1")
    public String hello1(){
        return "hello1";
    }

    @RequestMapping("/hello")
    /**权限启用-2 接口方法上使用注解
     * 还有两种注解@PostAuthorize,@Secured
     * */
    @PreAuthorize("@ex.hasAuthority('system:dept:list')")
//    @PreAuthorize("hasAnyAuthority('admin','test','system:dept:list')")
//    @PreAuthorize("hasRole('system:dept:list')")
//    @PreAuthorize("hasAnyRole('admin','system:dept:list')")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/testCors")
    public ResponseResult testCors(){
        return new ResponseResult(200,"testCors");
    }
}
