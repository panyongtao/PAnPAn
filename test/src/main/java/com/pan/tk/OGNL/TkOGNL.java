package com.pan.tk.OGNL;

import com.pan.tk.annotion.TkLogicDelete;
import com.pan.tk.helper.TkSqlHelper;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.OGNL;

import java.util.Iterator;
import java.util.Map;

public class TkOGNL extends OGNL {
    private static EntityField field;
    private EntityField entityField;
    public static String andNotLogicDelete(Object parameter){
        String result = "";
        if (parameter instanceof Example){
            Example example = (Example) parameter ;
            Map<String,EntityColumn> propertyMap = example.getPropertyMap();
            Iterator var4 = propertyMap. entrySet ().iterator() ;
            while (var4.hasNext()){
                Map.Entry<String,EntityColumn> entry = (Map.Entry) var4.next() ;EntityColumn column = (EntityColumn) entry.getValue() ;
                if (column.getEntityField().isAnnotationPresent(TkLogicDelete.class)) {
                    result = column.getColumn() +" = "+TkSqlHelper.getLogicDeletedValue(column, false);
                    if (example.getOredCriteria() != null && example.getOredCriteria().size() != 0) {
                        if (example.getOredCriteria().get(0).getCriteria().size() == 0){
                            result = result + " ";
                        } else {
                            result = result + " and " ;
                        }
                    }
                }
            }
        }
        return result;
    }
}

