package pan.voliate;

/**
 * @Author pan
 * @Date 2022/7/25 14:09
 * @Version 1.0
 * synchronized保证原子性,但粒度大
 */
public class TestVoliateSyn {
    volatile static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        for (int j = 0; j <10 ; j++) {
            new Thread(() -> {
                for (int k = 0; k <10000 ; k++) {
                    synchronized (o){
                        i++;
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(i);
    }
}
