package com.pan.tk;

import com.pan.tk.provider.TkMapperProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@RegisterMapper
public interface TkMapper<T> extends Mapper<T> {
    @SelectProvider(type = TkMapperProvider.class, method = "dynamicSQL")
    List<String> selectSingleColumn(String column);

    @SelectProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    List<String> selectSingleNoneNullColumn(String column) ;

    @SelectProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    String selectSeq(String seq);

    @SelectProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    T selectByJoinPrimaryKey(Object key) ;

    @SelectProvider(type = TkMapperProvider.class, method = "dynamicSQL")
    List<T> selectJoin(T record) ;

    @SelectProvider(type = TkMapperProvider.class, method = "dynamicSQL")
    List<T> selectByJoinExample(Object example);

    @SelectProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    List<T> selectByJoinExampleAndRowBounds(Object example,RowBounds rowBounds) ;

    @UpdateProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    void updateBatch(List<T> list);

    @UpdateProvider(type = TkMapperProvider.class,method = "dynamicSQL")
    void insertBatch(List<T> list);
    @SelectProvider(type = TkMapperProvider.class, method = "dynamicSQL")
    int selectTkCountByExample(Object example);

}
