package com.ok;

import com.Application;
import com.ejlchina.data.Mapper;
import com.ejlchina.okhttps.*;
import com.ejlchina.okhttps.Process;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class TestHttp {
    @Test
    public void test(){
        String url="www.baidu.com";
        //构建Http请求
        HTTP http = HTTP.builder().build();
        //同步请求
        http.sync(url);
        //异步请求
        http.async(url);
        http.sync(url).get()                         // GET请求
                 .getBody()                     // 获取响应报文体
                .toList(Object.class);           // 得到目标数据
        //异步请求
        http.async("/users/1")                //  http://api.example.com/users/1
                .setOnResponse((HttpResult res) -> {
                    // 得到目标数据
                    User  user = res.getBody().toBean(User.class);
                })
                .get();                       // GET请求
        //使用OkHttps
        OkHttps.async("https://api.example.com/auth/login")
                .addBodyPara("username", "jack")
                .addBodyPara("password", "xxxx")
                .setOnResponse((HttpResult result) -> {
                    // 得到返回数据，使用 Mapper 可省去定义一个实体类
                    Mapper mapper = result.getBody().toMapper();
                    // 登录是否成功
                    boolean success = mapper.getBool("success");
                })
                .post();
        //密码登录
        OkHttps.async("https://api.example.com/auth/login").basicAuth("u","p");
        //token登录
        OkHttps.async("https://api.example.com/auth/login").bearerAuth("");
        http.sync(url).put(); //支持get,put,post,head,delete,patch

        //同步请求的所有这些方法都会返回一个HttpResult，而异步请求则会返回一个HttpCall：
        HttpResult res1 = http.sync("/books").get();     // 同步 GET
        HttpResult res2 = http.sync("/books").post();    // 同步 POST

        HttpCall call1 = http.async("/books").get();     // 异步 GET
        HttpCall call2 = http.async("/books").post();    // 异步 POST

        //回调函数
        http.async("/users")        // http://api.demo.com/users
                .setOnResponse((HttpResult res) -> {
                    // 响应回调
                    int status = res.getStatus();       // 状态码
                    Headers headers = res.getHeaders(); // 响应头
                    HttpResult.Body body = res.getBody();          // 报文体
                })
                .setOnException((IOException e) -> {
                    // 异常回调
                })
                .setOnComplete((HttpResult.State state) -> {
                    // 完成回调，无论成功失败都会执行，并且在 响应|异常回调 之前执行
                    // 可以根据 state 枚举判断执行状态:
                    // State.CANCELED`      请求被取消
                    // State.RESPONSED`     已收到响应
                    // State.TIMEOUT`       请求超时
                    // State.NETWORK_ERROR` 网络错误
                    // State.EXCEPTION`     其它请求异常
                })
                .get();
        //便捷式回调,上面的回调优化
        http.async("/users")        // http://api.demo.com/users
                .setOnResBody(body -> {
                    // 得到响应报文体 Body 对象
                })
                .setOnResMapper(mapper -> {
                    // 得到响应报文体反序列化后的 Mapper 对象
                })
                .setOnResArray(array -> {
                    // 得到响应报文体反序列化后的 Array 对象
                })
                .setOnResString(str -> {
                    // 得到响应报文体的字符串 String 对象
                })
                .get();
        //上传
        http.sync("/upload")
                .addFilePara("avatar", "D:/image/avatar.jpg")
                .setOnProcess((Process  process) -> {
                    long doneBytes = process.getDoneBytes();   // 已上传字节数
                    long totalBytes = process.getTotalBytes(); // 总共的字节数
                    double rate = process.getRate();           // 已上传的比例
                    boolean isDone = process.isDone();         // 是否上传完成
                })
                .post();
        //下载
        http.sync("/download/test.zip").get().getBody()
                .setOnProcess((Process process) -> {
                    long doneBytes = process.getDoneBytes();   // 已下载字节数
                    long totalBytes = process.getTotalBytes(); // 总共的字节数
                    double rate = process.getRate();           // 已下载的比例
                    boolean isDone = process.isDone();         // 是否下载完成
                })
                .toFolder("D:/download/")        // 指定下载的目录，文件名将根据下载信息自动生成
                .start();
        http.sync("/download/test.zip").get().getBody()
                .toFile("D:/download/test.zip")              // 下载到文件
                .setOnSuccess((File file) -> {
                    // 下载成功
                })
                .setOnFailure((Download.Failure failure) -> {
                    // 下载失败
                })
                .start();
        //构建请求
        http.sync("").bodyType("application/json");
        http.sync("").bodyType("application/xml");
        http.sync("").bodyType("application/x-www-form-urlencoded");
        //也可以是如下的缩写形式：

        http.sync("").bodyType("json");
        http.sync("").bodyType("xml");
        http.sync("").bodyType("form");
        http.sync("").bodyType(OkHttps.JSON);
        //路径参数
        final String BOOKS_QUERY_URL = "/authors/{authorId}/books?type={type}";

        http.async(BOOKS_QUERY_URL)     // /authors/1/books?bookType=2
                .addPathPara("authorId", 1)
                .addPathPara("type", 2)
                .setOnResponse((HttpResult res) -> {
                }).get();
        //表单请求参数
        http.async("/projects")
                .addBodyPara("name", "OkHttps")
                .addBodyPara("desc", "最好用的网络框架")
                .post();
        //map
        Map<String, Object> params = new HashMap<>();
        params.put("name", "OkHttps");
        params.put("desc", "最好用的网络框架");
        http.async("/projects")
                .addBodyPara(params)
                .post();
        //构造
        http.async("/projects")
                .setBodyPara("name=OkHttps&desc=最好用的网络框架")
                .post();
        //异常处理
        // 方法  nothrow() 让异常不直接抛出
        HttpResult result = http.sync("/users/1").nothrow().get();
// 判断执行状态
        switch (result.getState()) {
            case RESPONSED:     // 请求已正常响应
                break;
            case CANCELED:      // 请求已被取消
                break;
            case NETWORK_ERROR: // 网络错误，说明用户没网了
                break;
            case TIMEOUT:       // 请求超时
                break;
            case EXCEPTION:     // 其它异常
                break;
        }
// 还可以获得具体的异常信息
        IOException error = result.getError();
        //异步处理异常
        http.async("/users/1")
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                })
                .get();
        //配置连接池
         http = HTTP.builder()
                .config((OkHttpClient.Builder builder) -> {
                    // 配置连接池 最小10个连接（不配置默认为 5）
                    builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
                })
                .build();
         //配置超时时间
         http = HTTP.builder()
                .config((OkHttpClient.Builder builder) -> {
                    // 连接超时时间（默认10秒）
                    builder.connectTimeout(10, TimeUnit.SECONDS);
                    // 写入超时时间（默认10秒）
                    builder.writeTimeout(10, TimeUnit.SECONDS);
                    // 读取超时时间（默认10秒）
                    builder.readTimeout(10, TimeUnit.SECONDS);
                })
                .build();
         //添加cookie
        http.async("https://...")
                // 添加两个 Cookie
                .addHeader("Cookie", "cname1=cvalue1; cname2=cvalue2")
                // ...
                .post();
    }
}
