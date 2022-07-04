package com.pan.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.dynamictable.DynamicEntity;
import com.pan.dynamictable.DynamicWrapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Mapper
public interface DynamicMapper extends BaseMapper<DynamicEntity> {

    int insert(@Valid DynamicEntity entity);

    int updateById(@Valid DynamicEntity entity);

    Map<String, Object> selectById(@Param("tableName") String tableName,@Param("id") Long id);

    @MapKey("id")
    List<Map<String, Object>> selectList(@Valid DynamicWrapper entity);

    int removeByIds(@Param("tableName")String tableName, @Param("ids")List<Long> ids);

    Long selectCount(@Valid DynamicWrapper entity);

    int existTable(@Param("tableName")String taleName);

    List<String> selectColumns(@Param("tableName")String taleName);

}