package lombok;

/**
 * @Author pan
 * @Date 2022/7/23 13:26
 * @Version 1.0
 * 作用在一个变量上
 * 默认调用资源的close()方法，如果该资源有其它关闭方法
 */
public class CleanUp {
    public static void main(String[] args) {
        @Cleanup Door door = new Door();
        door.function();
    }
    /**
     * @author chenjun
     */
    static class Door {

        /**
         * 门是否打开
         * true : 打开
         * false : 关闭
         */
        private boolean openStatus;

        public Door(boolean openStatus) {
            this.openStatus = openStatus;
        }

        public Door() {
            this.openStatus = true;
            System.out.println("初始化时，门的状态默认是-打开 ");
        }

        public void function() {
            System.out.println("调用该对象的某一个或者多个方法ing");
        }

        public void close() {
            System.out.println("关门之前，门的状态是-" + (this.openStatus ? "打开" : "关闭"));
            this.openStatus = false;
            System.out.println("关门之前，门的状态是-关闭");
        }

    }
}
