package aspect;

/**
 * @Author pan
 * @Date 2022/7/23 14:00
 * @Version 1.0
 */
public aspect MyAspect {
    pointcut HelloWorldPointCut() : execution(* main(..));

    before() : HelloWorldPointCut(){
        System.out.println("all cut====>");
    }
    void main() {
        System.out.println(11);
    }
}
