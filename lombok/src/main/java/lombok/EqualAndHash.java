package lombok;

/**
 * @Author pan
 * @Date 2022/7/23 13:53
 * @Version 1.0
 * •这个注解会生成equals(Object other) 和 hashCode()方法。
 * •它默认使用非静态，非瞬态的属性
 * •可通过参数exclude排除一些属性
 * •可通过参数of指定仅使用哪些属性
 * •它默认仅使用该类中定义的属性且不调用父类的方法
 * •可通过callSuper=true解决上一点问题。让其生成的方法中调用父类的方法。
 * ：@Data相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集。
 * 在使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解。（开发一般都会同时加上这两个注解）
 */
@EqualsAndHashCode
public class EqualAndHash {
}
