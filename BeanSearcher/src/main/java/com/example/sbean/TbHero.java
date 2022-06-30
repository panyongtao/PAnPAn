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
public class TbHero extends CommonSearcherBean<TbHero> implements Serializable {
    private static final long serialVersionUID = -30929629985905611L;
    
    private Integer id;
    
    private String username;
    
    private String profession;
    
    private String phone;
    
    private String email;
    
    private Date onlinetime;


}