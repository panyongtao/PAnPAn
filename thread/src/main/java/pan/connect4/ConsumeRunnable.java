package pan.connect4;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @Author pan
 * @Date 2022/7/25 14:50
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class ConsumeRunnable implements Runnable{
    private Product product=new Product();
    @Override
    @SneakyThrows
    public void run() {
        while (true){
            synchronized (product){
                //仓库空了就等待
                while(!product.flag){
                    product.wait();
                }
                System.out.println("消费者消费商品"+product.getName()+":"+product.getColor());
                //设置仓库为空
                product.flag=false;
                product.notify();
            }
        }
    }
}
