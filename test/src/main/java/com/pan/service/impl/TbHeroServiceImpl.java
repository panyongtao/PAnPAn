package com.pan.service.impl;

import com.pan.dao.TbHeroDao;
import com.pan.datasource.annotion.DS;
import com.pan.entity.TbHero;
import com.pan.pojo.TkHero;
import com.pan.service.TbHeroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TbHero)表服务实现类
 *
 * @author makejava
 * @since 2022-03-27 14:25:58
 */
@Service
public class TbHeroServiceImpl implements TbHeroService {
    @Resource
    private TbHeroDao tbHeroDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    @DS("Oracle")
    public TbHero queryById(Integer id) {
        return this.tbHeroDao.queryById(id);
    }

    @DS
    public TbHero queryById1(Integer id) {
        return this.tbHeroDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<TbHero> queryAllByLimit(int offset, int limit) {
        return this.tbHeroDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tbHero 实例对象
     * @return 实例对象
     */
    @Override
    public TbHero insert(TbHero tbHero) {
        this.tbHeroDao.insert(tbHero);
        return tbHero;
    }

    /**
     * 修改数据
     *
     * @param tbHero 实例对象
     * @return 实例对象
     */
    @Override
    public TbHero update(TbHero tbHero) {
        this.tbHeroDao.update(tbHero);
        return this.queryById(tbHero.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tbHeroDao.deleteById(id) > 0;
    }

    @Override
    public TbHero selectTkOne(Integer id) {
        new TkHero().selectAll();
        return null;
    }

    @Override
    @DS("Oracle")
    public TbHero selectTkTwo(Integer id) {
        new TkHero().selectAll();
        return null;
    }
}