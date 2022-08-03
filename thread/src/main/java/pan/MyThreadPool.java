package pan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author pan
 * @Date 2022/7/25 9:05
 * @Version 1.0
 * 定义的线程满的话会回收线程
 */
public class MyThreadPool {
    public static void main(String[] args) {
        //1 使用线程池的工厂类 Executors 的静态方法 newFixedThreadPool 生产一个指定数量的线程池
        ExecutorService es = Executors.newFixedThreadPool(4);
        //3 调用ExecutorService的submit()方法,传递线程实体类 → 开启线程,执行run()方法
        es.submit(new MyThread());
        es.submit(new MyThread());
        es.submit(new MyThread());
        es.submit(new MyThread());
        es.submit(new MyThread());
    }
}
