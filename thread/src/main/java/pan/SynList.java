package pan;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author pan
 * @Date 2022/7/25 10:04
 * @Version 1.0
 */
public class SynList {
    public static void main(String[] args) {
        //把一个集合转成同步集合
        Collections.synchronizedList(new ArrayList<>());
    }
}
