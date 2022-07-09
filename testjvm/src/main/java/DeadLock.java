class Book { }   // 描述图书的资源

class Paint { }   // 描述图画的资源

public class DeadLock {  // 李兴华高薪就业编程训练营
    public static void main(String[] args) throws Exception { // 沐言科技：www.yootk.com
        Book book = new Book();  // 图书资源
        Paint paint = new Paint(); // 图画资源
        Thread threadBook = new Thread(() -> {
            synchronized (paint) { // 锁定同步资源
                System.out.println("张三对王八说：你借我你的书，我再借你我的画，" +
                        "如果不先借我书我绝对不借你画。");
                try {   // 设置休眠以便于死锁问题的显现
                    Thread.sleep(1000);  // 让程序问题乖乖露出水面
                    synchronized (book) {
                        Thread.sleep(1000); // 让程序的问题乖乖露出水面
                        System.out.println("张三得到了古书，从此过上了认真修炼秘籍的生活。");
                    }
                } catch (InterruptedException e) {
                }
            }
        });
        Thread threadPaint = new Thread(() -> {
            synchronized (book) { // 锁定同步资源
                System.out.println("王八对张三说：你借我你的画，我再借你我的书，" +
                        "如果不先借我画我绝对不借你书。");
                try {  // 设置休眠以便于死锁问题的显现
                    Thread.sleep(1000);  // 让程序的问题乖乖露出水面
                    synchronized (paint) { // 线程同步
                        Thread.sleep(1000); // 让程序的问题乖乖露出水面
                        System.out.println("李四得到了名画，开始没日没夜的沉浸在图画的世界里。");
                    }
                } catch (InterruptedException e) {
                }
            }
        });
        threadBook.start(); // 线程启动
        threadPaint.start();   // 线程启动
    }
}


