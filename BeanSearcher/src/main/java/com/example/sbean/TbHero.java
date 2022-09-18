package com.example.sbean;

import com.example.service.CommonSearcherBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TbHero)实体类
 *
 * @author makejava
 * @since 2022-03-27 14:25:56
 */
@Data
//左连接@SearchBean(tables = "user u left join user_detail d on u.id = d.user_id")
//from子查询@SearchBean(tables = "(select id, name from user) t")
//关联@SearchBean(
//    tables = "user u, (select user_id, ... from ...) t",
//    where = "u.id = t.user_id"
//)

//where
//@SearchBean(
//        tables = "student s",
//        // 只查询考试均分及格的学生（Where 子查询）
//        where = "(select avg(sc.score) from student_course sc where sc.student_id = s.id) >= 60"
//)
public class TbHero extends CommonSearcherBean implements Serializable {
    private static final long serialVersionUID = -30929629985905611L;

//   运算符约束 @DbField(onlyOn = Equal.class)
    private Integer id;
    
    private String username;
    
    private String profession;
    
    private String email;

    private Date onlinetime;
    // @DbField("select sum(sc.score) from student_course sc where sc.student_id = s.id")

}