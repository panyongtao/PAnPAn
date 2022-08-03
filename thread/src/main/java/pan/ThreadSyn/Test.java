package pan.ThreadSyn;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @Author pan
 * @Date 2022/7/25 16:57
 * @Version 1.0
 */
public class Test {
    private static CountDownLatch countDownLatch=new CountDownLatch(3);
    //推倒之后，重新从设置的线程数量开始
    private static CyclicBarrier cyclicBarrier=new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"2个线程合并结果");
        }
    });
    //从0开始增长
    private static Semaphore semaphore=new Semaphore(0);
    /**
     * 信号量计数，不用指定计数
     */
    @SneakyThrows
    public static void main(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("子线程");
                    //释放许可证
                    semaphore.release();
                }
            }).start();
        }
        //拿到两个许可证的时候才可以往下执行
        semaphore.acquire(4);
        System.out.println("主线程");
    }
    /**
     * 测试回环屏障
     */
    @SneakyThrows
    public static void main4(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("子线程");
                    cyclicBarrier.await();
                }
            }).start();
        }
        System.out.println("主线程");
    }
    /**
     * 回环屏障
     */
    @SneakyThrows
    public static void main2(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    System.out.println("子线程");
                    cyclicBarrier.await();
                }
            }).start();
        }
        System.out.println("主线程");
    }
    /**
     * 线程计数器
     * @param args
     */
    @SneakyThrows
    public static void main1(String[] args) {
        for (int i = 0; i <3 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("子线程");
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("主线程");
    }
}
