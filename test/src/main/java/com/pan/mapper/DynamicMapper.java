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

/**
 * mybatis plus版本动态mapper
 * 最佳用法是根据表名动态操作表
 */
@Mapper
public interface DynamicMapper extends BaseMapper<DynamicEntity> {

    /**
     * 根据sql做动态查询
     * @param sql
     * @return
     */
    List<Map<String,Object>> querySql(@Param("sql") String sql);

    int insert(@Valid DynamicEntity entity);

    int updateById(@Valid DynamicEntity entity);

    Map<String, Object> selectById(@Param("tableName") String tableName,@Param("id") Long id);

    @MapKey("id")
    List<Map<String, Object>> selectList(@Valid DynamicWrapper entity);

    int removeByIds(@Param("tableName")String tableName, @Param("ids")List<Long> ids);

    Long selectCount(@Valid DynamicWrapper entity);

    /**
     * !!使用时mysql需要改数据库名
     * @param taleName
     * @return
     */
    int existTable(@Param("tableName")String taleName);
    /**
     * !!使用时mysql需要数据库名
     * @param taleName
     * @return
     */
    List<String> selectColumns(@Param("tableName")String taleName);
    /**
     * 查询oracle序列
     */
    Long selectSeq(@Param("seq")String seq);

}