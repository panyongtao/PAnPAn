package com.pan.controller;

import com.pan.annotation.CommonAuth;
import com.pan.annotation.ParamInfo;
import com.pan.entity.TbHero;
import com.pan.service.TbHeroService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TbHero)表控制层
 *
 * @author makejava
 * @since 2022-03-27 14:25:59
 */
@RestController
@RequestMapping("tbHero")
@ParamInfo
@CommonAuth
public class TbHeroController {
    /**
     * 服务对象
     */
    @Resource
    private TbHeroService tbHeroService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public TbHero selectOne(Integer id) {
        TbHero tbHero = this.tbHeroService.queryById(id);
        return tbHero;
    }

    @GetMapping("selectTwo")
    public TbHero selectTwo(Integer id) {
        TbHero tbHero = this.tbHeroService.queryById1(id);
        return tbHero;
    }

    @PostMapping("selectTkOne")
    public TbHero selectTkOne(Integer id) {
        TbHero tbHero = this.tbHeroService.selectTkOne(id);
        return tbHero;
    }

    @GetMapping("selectTkTwo")
    public TbHero selectTkTwo(Integer id) {
        TbHero tbHero = this.tbHeroService.selectTkTwo(id);
        return tbHero;
    }

    @PostMapping("test")
    public String test(Integer id) {
        return "le";
    }

}