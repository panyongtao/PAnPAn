package pan.pojo;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
public class Item implements Serializable {
    @Field("id")
    private Long id;
    @Field("item_title")
    private String title;
    @Field("item_price")
    private Double price;
    @Field("item_image")
    private String image;
    //导入指定数据库中的数据（这种导入方式无法导入动态域，动态域需要手写代码实现）
    @Dynamic
    @Field("item_spec_*")
    private Map<String, String> spec;
    @Field("item_brand")
    private String brand;
    @Field("item_category")
    private String category;
    @Field("item_seller")
    private String seller;
    @Field("item_goods_id")
    private Long goodsId;
    @Field("item_is_deleted")
    private Integer isDeleted;
    @Field("item_last_modified")
    private Date lastModified;
}

