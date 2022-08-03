package pan.connect5;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

/**
 * @Author pan
 * @Date 2022/7/25 14:31
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
public class ProduceRunnable implements Runnable {
    private Product product = new Product();

    @Override
    @SneakyThrows
    public void run() {
        int i = 0;
        while (true) {
                if (i % 2 == 0) {
                    product.produce("馒头","白色");
                } else {
                    product.produce("玉米饼","黄色");
                }
                i++;
        }
    }
}
