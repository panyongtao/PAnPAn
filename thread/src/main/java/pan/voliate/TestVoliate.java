package pan.voliate;

/**
 * @Author pan
 * @Date 2022/7/25 14:09
 * @Version 1.0
 * volatile 无法保证原子性
 */
public class TestVoliate {
    volatile static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j <10 ; j++) {
            new Thread(() -> {
                for (int k = 0; k <10000 ; k++) {
                    i++;
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(i);
    }
}
