package com.pan.service;

import com.pan.entity.TbHero;
import com.pan.pojo.TkHero;

import java.util.List;

/**
 * (TbHero)表服务接口
 *
 * @author makejava
 * @since 2022-03-27 14:25:58
 */
public interface TbHeroService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbHero queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TbHero> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tbHero 实例对象
     * @return 实例对象
     */
    TbHero insert(TbHero tbHero);

    /**
     * 修改数据
     *
     * @param tbHero 实例对象
     * @return 实例对象
     */
    TbHero update(TbHero tbHero);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    TbHero queryById1(Integer id);

    TkHero selectTkOne(Integer id);

    TkHero selectTkTwo(Integer id);
}