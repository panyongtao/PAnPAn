package pan;

import lombok.SneakyThrows;

/**
 * @Author pan
 * @Date 2022/7/25 9:11
 * @Version 1.0
 */
public class Join {
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
        //内部仍然时交替执行只是在最后的时候等待一下，且需要在start()之后执行
        wugui.join();
        //再执行兔子
        tuzi.start();
        //wugui.join();最后使用join是无效的
    }
}
