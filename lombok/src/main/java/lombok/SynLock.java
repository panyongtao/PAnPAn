package lombok;

/**
 * @Author pan
 * @Date 2022/7/23 13:37
 * @Version 1.0
 */
public class SynLock {
    private final Object readLock = new Object();

    @Synchronized
    public static void hello() {
        System.out.println("world");
    }

    @Synchronized("readLock")
    public void foo() {
        System.out.println("bar");
    }
}
