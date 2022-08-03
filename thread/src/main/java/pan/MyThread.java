package pan;

/**
 * @Author pan
 * @Date 2022/7/25 8:51
 * @Version 1.0
 */
public class MyThread extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println("thread:"+currentThread().getName());
    }

    public static void main(String[] args) {
        new MyThread().start();
    }
}
