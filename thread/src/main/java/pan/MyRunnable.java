package pan;

/**
 * @Author pan
 * @Date 2022/7/25 8:54
 * @Version 1.0
 */
public class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("runnable");
    }

    public static void main(String[] args) {
        new Thread(new MyRunnable()).start();
    }
}
