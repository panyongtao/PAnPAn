package com.pan.dynamictable.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.dynamictable.DynamicEntity;
import com.pan.dynamictable.DynamicWrapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Mapper
public interface DynamicMapper  extends BaseMapper<DynamicEntity> {

    int insert(@Valid DynamicEntity entity);

    int updateById(@Valid DynamicEntity entity);

    Map<String, Object> selectById(String tableName, Long id);

    @MapKey("id")
    List<Map<String, Object>> selectList(@Valid DynamicWrapper entity);

    int removeByIds(String tableName, List<Long> ids);

    Long selectCount(@Valid DynamicWrapper entity);

    int existTable(String taleName);

    List<String> selectColumns(String taleName);

}