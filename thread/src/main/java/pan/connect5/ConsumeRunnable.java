package pan.connect5;

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
            product.consume();
        }
    }
}
