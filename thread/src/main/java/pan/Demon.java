package pan;

/**
 * @Author pan
 * @Date 2022/7/25 9:26
 * @Version 1.0
 * 守护线程
 */
public class Demon {
    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }
        @Override
        public void run() {
            System.out.println(this.getName() + "开始执行," + (this.isDaemon() ? "我是守护线程" : "我是用户线程"));
            while (true) ;
        }
    }

    public static void main(String[] args) {
        T1 t1 = new T1("子线程1");
        //T1是相对于主线程的一个守护线程，对于子线程不起作用，子线程内部同样可以定义守护线程
//        setDaemon()方法必须在线程的start()方法之前调用，在后面调用会报异常，并且不起效
        t1.setDaemon(true);
        t1.start();
        Join.main(new String[]{});
        System.out.println("主线程结束");
    }
}
