package com.pan.dao;

import com.pan.entity.TbHero;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * (TbHero)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-27 14:25:57
 */

public interface TbHeroDao {

    List<TbHero> queryJoinCable(Integer id);

    List<Map<String,Object>> queryJoinCable1(Integer id);

    /**
     * 根据sql做动态查询
     * @param sql
     * @return
     */
    List<Map<String,Object>> querySql(String sql);

    /**
     * 根据sql做动态查询
     * @return
     */
    @Select("select t.*,c.cable_name,c.hero_id " +
            "                     from tb_hero t\n" +
            "                       left join cable c on t.id=c.hero_id")
    List<Map<String,Object>> querySql2();

    List<TbHero> queryJoinCables(List<Integer> list);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbHero queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbHero> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tbHero 实例对象
     * @return 对象列表
     */
    List<TbHero> queryAll(TbHero tbHero);

    /**
     * 新增数据
     *
     * @param tbHero 实例对象
     * @return 影响行数
     */
    int insert(TbHero tbHero);

    /**
     * 修改数据
     *
     * @param tbHero 实例对象
     * @return 影响行数
     */
    int update(TbHero tbHero);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}