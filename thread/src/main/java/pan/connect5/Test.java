package pan.connect5;

/**
 * @Author pan
 * @Date 2022/7/25 14:29
 * @Version 1.0
 * 线程通信必须和synchronized配合
 */
public class Test {
    /**
     *wait会释放锁也会sleep
     * @param args
     */
    public static void main(String[] args) {
        Product product = new Product();
        new Thread(new ProduceRunnable(product)).start();
        new Thread(new ConsumeRunnable(product)).start();
    }
}
