package pan;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author pan
 * @Date 2022/7/25 10:18
 * @Version 1.0
 * 锁比较灵活，可重入锁，锁可被多次锁定和释放
 * ReentrantLock唯一个属性类其他都是静态内部类
 * 锁的最后都是AQS (抽象的队列同步)
 */
public class Lock {
    public static void main(String[] args) {
        //默认是非公平锁，性能好true公平锁
        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            f(lock);
            try {
                System.out.println( Thread.currentThread().getName() + " do some thing");
            }finally {
                lock.unlock();
            }
        }).start();
    }

    private static void f(ReentrantLock lock) {

        try {
            lock.lock();
        }finally {
            lock.unlock();
        }
    }
}
