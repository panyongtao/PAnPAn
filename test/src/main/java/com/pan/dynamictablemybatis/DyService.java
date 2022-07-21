package com.pan.dynamictablemybatis;


import com.pan.mapper.DyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 其实这个动态表查询和mybatis没关系
 */
@Service
public class DyService{
    @Resource
    private DyMapper baseMapper;
    //todo table校验
    public List<Map<String,Object>> querySql(String sql){
        return baseMapper.querySql(sql);
    };

    public int save(DyEntity entity){
        return baseMapper.insert(entity);
    }

    public  List<String> selectColumns(String taleName) {
        return baseMapper.selectColumns(taleName);
    }

    public boolean updateById(DyEntity entity){
        return retBool(baseMapper.updateById(entity));
    }

    public Map<String, Object> getById(String tableName,Long id){
        return baseMapper.selectById(tableName,id);
    }

    public List<Map<String, Object>> list(DyWrapper wrapper){
        if(StringUtils.isBlank(wrapper.getColumns())){
            wrapper.setColumns("*");
        }
        if(!existTable(wrapper.getTableName())){
            return new ArrayList<>();
        }
        return baseMapper.selectList(wrapper);
    }

    public Long count(DyWrapper entity){
        return baseMapper.selectCount(entity);
    }

    public boolean existTable(String tableName){
        return retBool(baseMapper.existTable(tableName));
    }

    public  boolean removeByIds(String tableName, List<Long> ids){
        return retBool(baseMapper.removeByIds(tableName,ids));
    }

    public Long selectSeq(String seq){
        return baseMapper.selectSeq(seq);
    }

    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

}
