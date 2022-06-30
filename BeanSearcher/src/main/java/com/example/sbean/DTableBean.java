package com.example.sbean;

import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

@SearchBean(tables = ":table:")
@Data
public class DTableBean {
    @DbField("id")
    private Long id;

    @DbField("name")
    private String name;
}
