package com.pan.dynamictable;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.pan.mapper.DynamicMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 其实这个动态表查询和mybatis没关系
 */
@Service
public class DynamicService extends ServiceImpl<DynamicMapper,DynamicEntity> {
    //todo table校验
    public List<Map<String,Object>> querySql(String sql){
        return baseMapper.querySql(sql);
    };

    public  List<String> selectColumns(String taleName) {
        return baseMapper.selectColumns(taleName);
    }

    public boolean updateById(DynamicEntity entity){
        return SqlHelper.retBool(baseMapper.updateById(entity));
    }

    public Map<String, Object> getById(String tableName,Long id){
        return baseMapper.selectById(tableName,id);
    }

    public List<Map<String, Object>> list(DynamicWrapper wrapper){
        if(StringUtils.isBlank(wrapper.getColumns())){
            wrapper.setColumns("*");
        }
        if(!existTable(wrapper.getTableName())){
            return new ArrayList<>();
        }
        return baseMapper.selectList(wrapper);
    }

    public Long count(DynamicWrapper entity){
        return baseMapper.selectCount(entity);
    }

    public boolean existTable(String tableName){
        return SqlHelper.retBool(baseMapper.existTable(tableName));
    }

    public  boolean removeByIds(String tableName, List<Long> ids){
        return SqlHelper.retBool(baseMapper.removeByIds(tableName,ids));
    }
    public Long selectSeq(String seq){
        return baseMapper.selectSeq(seq);
    }

}
