package com.example.sbean;

import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

@SearchBean(tables = "employee")
@Data
public class DFieldBean {
    @DbField("id")
    private Long id;

    @DbField(":fieldName:")
    private String field;
}
