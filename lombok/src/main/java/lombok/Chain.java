package lombok;

import lombok.experimental.Accessors;

/**
 * @Author pan
 * @Date 2022/7/23 13:13
 * @Version 1.0
 * set方法返回的时true
 */
@Data
@Accessors(chain=true)
public class Chain {
    private String name;
    private int id;

    public static void main(String[] args) {
        val chain = new Chain();
        System.out.println(chain.setName("pan"));
    }
}
