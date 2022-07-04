package com.pan.dynamictable;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("dynamic")
public class DynamicController {

    private final DynamicService dynamicService;

    @PostMapping("page")
    public R<IPage<Map<String,Object>>> pageList(@RequestBody @Valid DynamicWrapper wrapper) {
        IPage page= new Page();
        page.setTotal(dynamicService.count(wrapper));
        Long pages;
        if(page.getTotal()%page.getSize()==0){
            pages=page.getTotal()/page.getSize();
        }else{
            pages=page.getTotal()/page.getSize()+1;
        }
        page.setPages(pages);
        page.setRecords(dynamicService.list(wrapper));
        return R.data(page);
    }

    @PostMapping("saveData")
    public R<Boolean> saveData(@RequestBody List<DynamicEntity> list) {
        List<DynamicEntity> entityList=new ArrayList<>();
        return  R.status(dynamicService.saveBatch(entityList));
    }

    @PostMapping("saveBatch/{tableName}")
    public R<List<Long>> saveBatch(@PathVariable("tableName") String tableName,@RequestBody List<Map<String,Object>> maps) {
        List<DynamicEntity> list=maps.stream().map(e->{
            DynamicEntity entity=new DynamicEntity();
            entity.setId(IdWorker.getId(DynamicEntity.class));
            entity.setParams(e);
            entity.setTableName(tableName);
            return entity;
        }).collect(Collectors.toList());
        dynamicService.saveBatch(list);
        return  R.data(list.stream().map(DynamicEntity::getId).collect(Collectors.toList()));
    }

    @GetMapping("get/{tableName}/{id}")
    public R<Map<String, Object>> get(@PathVariable("tableName") String tableName,@PathVariable("id") Long id) {
        return R.data(dynamicService.getById(tableName,id));
    }

    @PostMapping("save")
    public R<Boolean> save(@RequestBody @Valid DynamicEntity entity) {
        return R.status(dynamicService.save(entity));
    }

    @PostMapping("update")
    public R<Boolean> update(@RequestBody @Valid DynamicEntity entity) {
        return R.status(dynamicService.updateById(entity));
    }

    @PostMapping("delete/{tableName}")
    public R<Boolean> delete(@PathVariable("tableName")String tableName,@RequestBody List<Long> ids) {
        return R.status(dynamicService.removeByIds(tableName,ids));
    }
}
