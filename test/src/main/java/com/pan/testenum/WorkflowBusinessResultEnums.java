package com.pan.testenum;

import com.pan.util.DynamicEnumUtil;
import lombok.SneakyThrows;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum WorkflowBusinessResultEnums {
    INIT(  "00","符提交"),
    START(  "01","初审");


    private String code;
    private String name;
    public static Map<String, WorkflowBusinessResultEnums> enumMap = new HashMap<>();

    static{
        //  可以在这里加载枚举的配置文件 比如从 properties  数据库中加载
        //  加载完后 使用DynamicEnumUtil.addEnum 动态增加枚举值
        //  然后正常使用枚举即可
        EnumSet<WorkflowBusinessResultEnums> set = EnumSet.allOf(WorkflowBusinessResultEnums.class);
        for (WorkflowBusinessResultEnums each: set ) {
            // 增加一个缓存 减少对枚举的修改
            enumMap.put(each.code, each);
        }
    }

    WorkflowBusinessResultEnums(String code , String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据关键字段获取枚举值  可以在这里做一些修改 来达到动态添加的效果
    public WorkflowBusinessResultEnums getEnum(String code){
        // 这里可以做一些修改  比如若从 enumMap 中没有取得 则加载配置动态添加
        return enumMap.get(code);
    }

    //  动态添加方法  添加完后加入缓存 减少对枚举的修改
    @SneakyThrows
    public static WorkflowBusinessResultEnums addEnum(String enumName, String value, String name){
        for (WorkflowBusinessResultEnums resultEnums : WorkflowBusinessResultEnums.values()) {
            if(value.equals(resultEnums.getCode())){
                throw new Exception("已存在同名的枚举[code:"+value+",name:"+name+"]不再创建此枚举");
            }
        }
        WorkflowBusinessResultEnums resultEnums = DynamicEnumUtil.addEnum(WorkflowBusinessResultEnums.class, enumName,
                new Class[]{String.class, String.class}, new Object[]{value, name});
        enumMap.put(value,resultEnums);
        return resultEnums;
    }
}