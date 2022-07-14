package pan.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class Item implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private String image;
    //导入指定数据库中的数据（这种导入方式无法导入动态域，动态域需要手写代码实现）
    private Map<String, String> spec;
    private String brand;
    private String category;
    private String seller;
    private Long goodsId;
    private Integer isDeleted;
    private Date lastModified;
}

