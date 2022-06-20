package com.pan.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 技能表(Cable)实体类
 *
 * @author 潘永涛
 * @since 2022-04-03 09:38:30
 */
@Data
public class Cable implements Serializable {
    private static final long serialVersionUID = -88607080732686361L;
    
    private Integer id;
    
    private String cableName;
    /**
    * 英雄id
    */
    private Integer heroId;

}