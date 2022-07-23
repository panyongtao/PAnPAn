package lombok;

/**
 * @Author pan
 * @Date 2022/7/23 13:20
 * @Version 1.0
 * 作用于属性上，提供关于此参数的非空检查，如果参数为空，则抛出空指针异常。
 * 此时需要加上
 * @NoArgsConstructor
 * @AllArgsConstructor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//作用于类上，由类中所有带有@NonNull注解或者带有final修饰的成员变量作为参数生成构造方法。
@RequiredArgsConstructor
public class Non {
    private int id;
    @NonNull
    private String remark;

    public static void main(String[] args) {
        Non non = new Non();
        non.setRemark("pan");
        System.out.println(non);
        non.setRemark(null);

    }
}
