package pan;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author pan
 * @Date 2022/7/25 9:05
 * @Version 1.0
 * 定义的线程满的话会回收线程
 */
public class ManyThreadPool {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Future> list=new ArrayList<>();
        for (int i = 0; i <200 ; i++) {
            MyCallable callable = new MyCallable();
            Future<String> task = pool.submit(callable);
            list.add(task);
//            System.out.println(task.get());task.get()这一步会影响性能，需要先把任务分出去
        }
        pool.shutdown();
    }
}
