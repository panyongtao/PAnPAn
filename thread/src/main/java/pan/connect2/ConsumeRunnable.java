package pan.connect2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    public void run() {
        while (true){
            System.out.println("消费者消费商品"+product.getName()+":"+product.getColor());
        }
    }
}
