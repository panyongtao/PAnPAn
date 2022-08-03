package pan.voliate;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author pan
 * @Date 2022/7/25 14:09
 * @Version 1.0
 * volatile 无法保证原子性
 */
public class TestVoliateAtom {
    volatile static AtomicInteger i = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j <10 ; j++) {
            new Thread(() -> {
                for (int k = 0; k <10000 ; k++) {
                    i.incrementAndGet();
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(i);
    }
}
