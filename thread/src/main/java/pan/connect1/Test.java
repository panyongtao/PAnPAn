package pan.connect1;

/**
 * @Author pan
 * @Date 2022/7/25 14:29
 * @Version 1.0
 */
public class Test {
    /**
     * 此时出现了线程问题
     * @param args
     */
    public static void main(String[] args) {
        Product product = new Product();
        new Thread(new ProduceRunnable(product)).start();
        new Thread(new ConsumeRunnable(product)).start();
    }
}
