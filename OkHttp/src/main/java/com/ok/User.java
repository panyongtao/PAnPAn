package com.ok;

import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

import java.util.Date;

@Data
@SearchBean
public class User {
    private String gender;
    private String name;
    private String age;
    private Date time;
}