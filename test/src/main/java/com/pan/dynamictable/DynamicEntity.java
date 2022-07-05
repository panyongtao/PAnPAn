package com.pan.dynamictable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Data
public class DynamicEntity {
    /** 主键 */
    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private long id;
    private String tableName;
    private Map<String,Object> params;
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void setParams(Map<String, Object> params) {
        params.forEach((k,v)->{
            if(v instanceof String){
                try {
                    Date date= dateFormat.parse(v.toString());
                    params.put(k,date);
                } catch (ParseException e) {
                    params.put(k,v);
                }
            }
        });
        params.put("id",id);
//        params.put("create_time",new Date());
        this.params = params;
    }
}