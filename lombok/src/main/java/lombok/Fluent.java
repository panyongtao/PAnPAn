package lombok;

import lombok.experimental.Accessors;

/**
 * @Author pan
 * @Date 2022/7/23 13:47
 * @Version 1.0
 */
@Data
@Accessors(fluent = true)
public class Fluent {
    private Long id;
    private String name;
    //生成的getter和setter方法如下，方法体略
    public Long id(){
        return id;
    }
    public Fluent id(Long id){
        this.id=id;
        return this;
    }
    public String name () {
        return name;
    }
    public Fluent name (String name) {
        this.name=name;
        return this;
    }

}
