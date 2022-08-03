package pan.connect3;

/**
 * @Author pan
 * @Date 2022/7/25 14:29
 * @Version 1.0
 */
public class Test {
    /**
     * 生产者和消费者此时同时上锁解决了商品问题
     * 但出现了生产过剩，或者消费过剩问题
     * @param args
     */
    public static void main(String[] args) {
        Product product = new Product();
        new Thread(new ProduceRunnable(product)).start();
        new Thread(new ConsumeRunnable(product)).start();
    }
}
