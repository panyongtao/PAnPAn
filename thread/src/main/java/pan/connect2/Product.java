package pan.connect2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author pan
 * @Date 2022/7/25 14:32
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
   private String name;
   private String color;
   boolean flag=false;//没有商品
   private Lock lock=new ReentrantLock();
   private Condition produceCondition=lock.newCondition();
   private Condition consumeCondition=lock.newCondition();

    /**
     * 生产一个产品
     */
    @SneakyThrows
    public void produce(String name,String color){
        lock.lock();
        if (flag) {
            produceCondition.await();
        }
        this.name=name;
        Thread.sleep(1);
        this.color=color;
        System.out.println("生产者生产产品"+name+"  "+color);
        flag=true;
        //通知消费者可以消费了notify()
        consumeCondition.signal();
        lock.unlock();
    }
    /**
     * 消费一个产品
     */
    @SneakyThrows
    public void consume(){
        lock.lock();
        if(!flag){
            consumeCondition.await();
        }
        System.out.println("消费者消费产品"+getName()+"  "+getColor());
        //改变状态没有商品了
        //通知生产者生产this.notify
        produceCondition.signal();
        lock.unlock();
    }
}
