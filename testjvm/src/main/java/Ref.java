import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用“=”
 * 软引用  内存不足会回收
 * 弱引用  被垃圾回收器发现就会回收
 */
public class Ref {
    public static void main(String[] args) {
        // 让对象不进行入池处理，是为了便于其方便的回收对象空间
        String str = new String("沐言科技：www.yootk.com"); // 实例化新的String对象
        WeakReference<String> ref = new WeakReference<>(str); // 将对象保存在软引用结构之中
        str = null; // 放弃对未入池字符串对象引用地址
        System.gc(); // 执行FullGC的处理操作
        System.out.println(ref.get()); // 获取数据
        System.out.println(Integer.MAX_VALUE);
    }
    public static void main1(String[] args) {
        // 让对象不进行入池处理，是为了便于其方便的回收对象空间
        String str = new String("沐言科技：www.yootk.com"); // 实例化新的String对象
        SoftReference<String> ref = new SoftReference<>(str); // 将对象保存在软引用结构之中
        str = null; // 放弃对未入池字符串对象引用地址
        System.gc(); // 执行FullGC的处理操作
        System.out.println(ref.get()); // 获取数据
    }
}
