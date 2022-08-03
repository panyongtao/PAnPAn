package pan;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author pan
 * @Date 2022/7/25 8:56
 * @Version 1.0
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "mycallable";
    }

    @SneakyThrows
    public static void main(String[] args) {
        FutureTask  futureTask = new FutureTask<String>(new MyCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
