package pan.connect4;

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
            synchronized (product){
                //仓库满了就等待
                while(product.flag){
                    product.wait();
                }
                if (i % 2 == 0) {
                    product.setName("馒头");
                    Thread.sleep(10);
                    product.setColor("白色");

                } else {
                    product.setName("玉米饼");
                    Thread.sleep(10);
                    product.setColor("黄色");
                }
                System.out.println("生产者生产了" + product.getName()+":"+product.getColor());
                //修改仓库状态满了
                product.flag=true;
                product.notify();
            }
            i++;
        }
    }
}
