package pan;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author pan
 * @Date 2022/7/25 10:40
 * @Version 1.0
 * 适用于读的次数超过写的次数
 * 读的时候并发执行，写的时候同步执行
 */
public class WriteReadLock {
    public static void main(String[] args) {
        //ReadwriteLock rwl = new ReentrantReadwriteLock ( );
        // 非公平可重入分为读锁和写锁
        ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
        ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
        ReentrantReadWriteLock.ReadLock readLock2 = rwl.readLock ( ) ;
        ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock ();
        ReentrantReadWriteLock.WriteLock writeLock2 = rwl.writeLock ( ) ;
        System.out.println (readLock ==readLock2) ;
        System.out.println (writeLock ==writeLock2) ;
        readLock.lock() ;
        writeLock.lock ();
        readLock.unlock () ;
        writeLock.unlock ();
        //读读共享读写互斥写军互斥

    }
}
