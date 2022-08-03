package pan;

import lombok.SneakyThrows;

/**
 * @Author pan
 * @Date 2022/7/25 9:45
 * @Version 1.0
 */
public class Yeild {
    @SneakyThrows
    public static void main(String[] args) {
        Thread wugui = new Thread(() -> {
            int i = 0;
            while (i <= 100) {
                i++;
                System.out.println("乌龟跑" + i);
            }
        });
        Thread tuzi = new Thread(() -> {
            int i = 0;
            while (i <= 100) {
                i++;
                System.out.println("兔子跑" + i);
            }
        });
        //阻塞乌龟
        wugui.start();
        //把线程变为就绪状态，效果不明显
        Thread.yield();
        //再执行兔子
        tuzi.start();
        //wugui.join();最后使用join是无效的
    }
}
