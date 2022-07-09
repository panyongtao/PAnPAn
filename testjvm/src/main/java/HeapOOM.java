import java.util.ArrayList;
import java.util.List;

/*** VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * -Xms20m：堆的最小值为20MB
 * -Xmx20m：堆的最大值为20MB   新生代+老年代
 * -XX:+HeapDumpOnOutOfMemoryError 让虚拟机在出现内存溢出异常时Dump出当前的内存堆转储快照
 * -XX:HeapDumpPath=E:/a.dump      设置堆溢出时文件存储位置
 * -XX:+PrintGC         打印GC日志
 * -XX:+PrintGCDetails  打印详细的GC信息
 * -XX:+PrintHeapAtGC    显示所有的堆信息
 * -Xss 设置每个线程栈大小，减少此值也就意味可以产生更多的线程
 * -XX:MetaspaceSize=1k 设置元空间大小   默认为20.8M  类的字节码信息会存储到元空间中
 * -XX:MaxMetaspaceSize=1k 设置元空间最大容量
 * -XX:MinMetaspaceFreeRatio 设置元空间GC后最小的剩余空间百分比 默认为40
 * -XX:MaxMetaspaceFreeRatio 设置元空间GC后最大的剩余空间百分比 默认为70
 **/

/**
 * jvm优化
 * 目前使用的都是HotSpot虚拟机
 * 类加载器的使用场景解决类冲突，热启动，jar包加密
 * jvm调优主要是针对堆调优
 * JUC原子类，外部内存
 * JIT即时编译器针对重复的热点代码进行二次编译成本地机器码
 * .java-.class-类加载器-字节码校验器-jvm解释器-jit
 * 分为C1(Client)和C2(server)目前默认时C2，采用分层
 * 混合模式是解释器模式和即时编译模式
 *
 * jvm内存划分
 * 堆新生代（from区和to区），老年代，
 * 元空间（直接物理内存非JVM开辟的空间）
 * 会产生内存碎片，STW问题
 * 垃圾回收器分类，串行，并行，CMS,G1（最大支持64G）,ZGC（解决大内存分配，仅适用于linux当中）
 */
public class HeapOOM {
    static class OOMObject {}

    public static void main(String[] args) throws Exception {
        List<OOMObject> list = new ArrayList<>();

        while (true){
            list.add(new OOMObject());
        }
    }
}