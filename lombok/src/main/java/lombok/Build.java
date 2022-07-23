package lombok;

/**
 * @Author pan
 * @Date 2022/7/23 13:40
 * @Version 1.0
 */
@Builder(builderMethodName="b",buildMethodName = "p")
public class Build {
    private String name;

    public static void main(String[] args) {
        System.out.println(Build.b().name("pan").p());
    }
}
