package pan.connect;

/**
 * @Author pan
 * @Date 2022/7/25 14:29
 * @Version 1.0
 */
public class Test {
    /**
     * 消费者产品是null
     */
    @org.junit.Test
    public void test1(){
        new Thread(new ProduceRunnable()).start();
        new Thread(new ConsumeRunnable()).start();
    }

    /**
     * 消费者产品是null
     */
    @org.junit.Test
    public void test3(){
        Product product = new Product();
        new Thread(new ProduceRunnable(product)).start();
        new Thread(new ConsumeRunnable(product)).start();
    }

    @org.junit.Test
    public void test2(){
        Product product = new Product ();
        Runnable runnable1 = new ProduceRunnable(product);
        Thread thread1 = new Thread (runnable1);
        Runnable runnable2 = new ConsumeRunnable (product);
        Thread thread2 = new Thread (runnable2) ;
        thread1. start();
        thread2 .start();

    }
}
