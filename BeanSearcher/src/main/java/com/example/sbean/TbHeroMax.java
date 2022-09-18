package com.example.sbean;

import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

import java.io.Serializable;

/**
 * (TbHero)实体类
 *
 * @author makejava
 * @since 2022-03-27 14:25:56
 */
@Data
@SearchBean(tables="(select max(id) ida from tb_hero) t")
public class TbHeroMax  implements Serializable {
    private static final long serialVersionUID = -30929629985905611L;
    @DbField("t.ida")
    private Integer ida;
}