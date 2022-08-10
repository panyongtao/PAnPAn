package com.pan.dynamictablemybatis;

import cn.hutool.extra.spring.SpringUtil;
import com.pan.mapper.DyMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author pan
 * @Date 2022/8/9 10:46
 * @Version 1.0
 */
public interface DyService {
    default DyMapper baseMapper(){
         return SpringUtil.getBean(DyMapper.class);
     };
    //todo table校验
    default List<Map<String,Object>> querySql(String sql){
        return baseMapper().querySql(sql);
    };

    default int save(DyEntity entity){
        return baseMapper().insert(entity);
    }

    default  List<String> selectColumns(String taleName) {
        return baseMapper().selectColumns(taleName);
    }

    default boolean updateById(DyEntity entity){
        return retBool(baseMapper().updateById(entity));
    }

    default Map<String, Object> getById(String tableName,Long id){
        return baseMapper().selectById(tableName,id);
    }

    default List<Map<String, Object>> list(DyWrapper wrapper){
        if(StringUtils.isBlank(wrapper.getColumns())){
            wrapper.setColumns("*");
        }
        if(!existTable(wrapper.getTableName())){
            return new ArrayList<>();
        }
        return baseMapper().selectList(wrapper);
    }

    default Long count(DyWrapper entity){
        return baseMapper().selectCount(entity);
    }

    default boolean existTable(String tableName){
        return retBool(baseMapper().existTable(tableName));
    }

    default  boolean removeByIds(String tableName, List<Long> ids){
        return retBool(baseMapper().removeByIds(tableName,ids));
    }

    default Long selectSeq(String seq){
        return baseMapper().selectSeq(seq);
    }

    static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }
}
