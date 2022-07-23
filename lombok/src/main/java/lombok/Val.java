package lombok;

import java.util.HashMap;

/**
 * @Author pan
 * @Date 2022/7/23 13:38
 * @Version 1.0
 */
public class Val {

    public static void main(String[] args) {
        val map = new HashMap<String, String>();
        map.put("1", "a");
        System.out.println(map);
    }
}
