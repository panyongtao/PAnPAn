package pan;

/**
 * @Author pan
 * @Date 2022/7/25 10:00
 * @Version 1.0
 * 线程同步方案
 * 1.synchronized块
 * 2.方法上加synchronzied
 * 3.方法加注解@synchronized
 * 4.voliate（可见性，有序性）+CAS（原子性）无锁化方案
 * 5.Lock锁+ReentrantLock（可重入锁，轻量级，性能高）
 * ！！ synchronized缺点可能因为线程阻塞导致锁无法释放
 * 于是出现了Lock锁，分为偏向锁，轻量锁，重量锁
 * 读锁，写锁，读写锁
 *
 * 锁的粒度，无锁，偏向锁，轻量锁，重量锁
 */
public class Syn {

}
