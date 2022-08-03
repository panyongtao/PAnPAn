package pan.voliate;

/**
 * @Author pan
 * @Date 2022/7/25 11:12
 * @Version 1.0
 * volatile 可以保证多个线程之间的数据可见性,有序性，但无法保证操作的原子性
 * 弱同步
 * synchronized都可以保证
 * 线程三要性，原子性，可见性，有序性
 */
public class MyVoliate {
    private volatile static boolean flag = true;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    System.out.println("-EeEEEEEeE=");
                }
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        //此处更改是不会影响线程内部的，所以需要用关键字voliate
        flag = false;
    }
}
