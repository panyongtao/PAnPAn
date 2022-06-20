package com.pan.tk.helper;

import cn.hutool.core.util.ClassUtil;
import com.pan.tk.OGNL.TkOGNL;
import com.pan.tk.annotion.*;
import tk.mybatis.mapper.LogicDeleteException;
import tk.mybatis.mapper.annotation.Version;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

public class TkSqlHelper extends SqlHelper {
    public static String getTableName(Class<?> entityClass) {
        Table tableAno = entityClass.getAnnotation(Table.class);
        return tableAno.name();
    }

    public static String exampleOrderBy(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"orderByClause != null\">");
        sql.append("order by $ forderByClause}");
        sql.append("</if>");
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sql.append("<if test=\"orderByClause == null\">");
            sql.append("ORDER BY " + orderByClause);
            sql.append("</if>");
        }
        return sql.toString();
    }

    public static String getTkLogicDelete(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = TkSqlHelper.hasLogicDeleteColumn(entityClass);
        if (hasLogicDelete) {
            sql.append(TkSqlHelper.whereLogicDelete(entityClass, false));
        }
        return sql.toString();
    }

    public static String getTableNameDot(Class<?> entityClass) {
        Table tableAno = entityClass.getAnnotation(Table.class);
        return tableAno.name() + ".";
    }

    public static String getAllColumns(Class<?> entityClass) {
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        Iterator var3 = columnSet.iterator();
        while (var3.hasNext()) {
            EntityColumn entityColumn = (EntityColumn) var3.next();
            sql.append(getTableNameDot(entityClass) + entityColumn.getColumn()).append(",");
        }
        return sql.substring(0, sql.length() - 1);
    }

    public static String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT");
        sql.append(getAllColumns(entityClass));
        //添加关联表字段
        Field[] declaredFields = entityClass.getDeclaredFields();
        sql.append(forFields(declaredFields));
        sql.append(" ");
        return sql.toString();
    }

    public static String exampleSelectColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<choose>");
        sql.append("<when test=\"@tk. mybatis.mapper.util.OGNL@hasSelectColumns(_parameter)\">");
        sql.append("<foreach collection=\"_parameter.selectColumns\" item=\"selectColumn\" separator=\",\">");
        sql.append("${selectColumn}");
        sql.append("</foreach>");
        sql.append("</when>");
        sql.append("<otherwise>");
        sql.append(getAllColumns(entityClass));
        Field[] declaredFields = entityClass.getDeclaredFields();
        sql.append(forFields(declaredFields));
        sql.append("</otherwise>");
        sql.append("</choose>");
        return sql.toString();
    }

    private static String forFields(Field[] declaredFields) {
        StringBuilder sql = new StringBuilder();
        for (Field field : declaredFields) {
            JoinKey annotation = field.getAnnotation(JoinKey.class);
            Transient trano = field.getAnnotation(Transient.class);
            if (annotation != null && trano != null) {
                String targetTable = annotation.value();
                if (!"".equals(targetTable)) {
                    ColumnAlias alias = field.getAnnotation(ColumnAlias.class);
                    if (alias != null) {
                        String value = alias.value();
                        if (StringUtil.isNotEmpty(value)) {
                            sql.append(" ," + targetTable + "." + StringUtil.camelhumpToUnderline(field.getName()));
                            sql.append(" as " + value);
                            continue;
                        }
                    }
                    sql.append("," + targetTable + "." + StringUtil.camelhumpToUnderline(field.getName()));
                }
            }
        }
        return sql.toString();
    }

    public static String wherePKColumns(Class<?> entityClass, String entityName, boolean useVersion) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = TkSqlHelper.hasLogicDeleteColumn(entityClass);
        sql.append("<where>");
        Set<EntityColumn> columnSet = EntityHelper.getPKColumns(entityClass);
        Iterator var6 = columnSet.iterator();
        while (var6.hasNext()) {
            EntityColumn column = (EntityColumn) var6.next();
            sql.append(" AND ").append(getTableNameDot(entityClass) + column.getColumnEqualsHolder(entityName));
        }
        if (useVersion) {
            sql.append(SqlHelper.whereVersion(entityClass));
        }
        if (hasLogicDelete) {
            sql.append(whereLogicDelete(entityClass, false));
        }
        sql.append("</where>");
        return sql.toString();
    }

    public static String whereLogicDelete(Class<?> entityClass, boolean isDeleted) {
        String value = logicDeleteColumnEqualsValue(entityClass, isDeleted);
        return "".equals(value) ? "" : "AND " + value;
    }

    public static String logicDeleteColumEqualsValue(Class<?> entityClass, boolean isDeleted) {
        EntityColumn logicDeleteColumn = getLogicDeleteColumn(entityClass);
        return logicDeleteColumn != null ? logicDeleteColumnEqualsValue(logicDeleteColumn, isDeleted, entityClass) : "";
    }

    public static String logicDeleteColumnEqualsValue(EntityColumn column, boolean isDeleted, Class<?> entityClass) {
        String result = "";
        if (column.getEntityField().isAnnotationPresent(TkLogicDelete.class)) {
            result = getTableNameDot(entityClass) + column.getColumn() + " =  " + getLogicDeletedValue(column, isDeleted);
        }
        return result;
    }

    public static int getLogicDeletedValue(EntityColumn column, boolean isDeleted) {
        if (!column.getEntityField().isAnnotationPresent(TkLogicDelete.class)) {
            throw new LogicDeleteException(column.getColumn() + "没有@TkLogicDelete 注解!");
        } else {
            TkLogicDelete logicDelete = (TkLogicDelete) column.getEntityField().getAnnotation(TkLogicDelete.class);
            return isDeleted ? logicDelete.isDeletedValue() : logicDelete.notDeletedValue();
        }
    }

    public static boolean hasLogicDeleteColumn(Class<?> entityClass) {
        return getLogicDeleteColumn(entityClass) != null;
    }

    public static EntityColumn getLogicDeleteColumn(Class<?> entityClass) {
        EntityColumn logicDeleteColumn = null;
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        boolean hasLogicDelete = false;
        Iterator var4 = columnSet.iterator();
        while (var4.hasNext()) {
            EntityColumn column = (EntityColumn) var4.next();
            if (column.getEntityField().isAnnotationPresent(TkLogicDelete.class)) {
                if (hasLogicDelete) {
                    throw new LogicDeleteException(entityClass.getCanonicalName() + "中包含多个带有@TkLogicDelete注解的字段," +
                            "个类中只能存在一个带有@TkLogicDelete注解的字段!");
                }
                hasLogicDelete = true;
                logicDeleteColumn = column;
            }
        }
        return logicDeleteColumn;
    }

    public static String orderByDefault(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if (orderByClause.length() > 0) {
            sql.append(" ORDER BY ");
            sql.append(getTableNameDot(entityClass) + orderByClause);
        }
        return sql.toString();
    }

    public static String whereAllIfColumns(Class<?> entityClass, boolean empty, boolean useVersion) {
        StringBuilder sql = new StringBuilder();
        boolean hasLogicDelete = false;
        sql.append("<where>");
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        EntityColumn logicDeleteColumn = TkSqlHelper.getLogicDeleteColumn(entityClass);
        Iterator var7 = columnSet.iterator();
        Table tableAno = entityClass.getAnnotation(Table.class);
        String curTableName = "";
        if (tableAno != null) {
            curTableName = tableAno.name();
        }
        while (true) {
            while (true) {
                EntityColumn column;
                do {
                    if (!var7.hasNext()) {
                        if (useVersion) {
                            sql.append(SqlHelper.whereVersion(entityClass));
                        }
                        if (hasLogicDelete) {
                            sql.append(TkSqlHelper.whereLogicDelete(entityClass, false));
                        }
                        sql.append("</where>");
                        return sql.toString();
                    }
                    column = (EntityColumn) var7.next();
                } while (useVersion && column.getEntityField().isAnnotationPresent(Version.class));
                if (logicDeleteColumn != null && logicDeleteColumn == column) {
                    hasLogicDelete = true;
                } else {
                    String targetTable = column.getTable().getName() + ".";
                    sql.append(SqlHelper.getIfNotNull(column, " AND " + targetTable + column.getColumnEqualsHolder(), empty));
                }
            }
        }
    }

    public static String exampleWhereClause() {
        Class<TkOGNL> ognlClass = TkOGNL.class;
        String className = ClassUtil.getClassName(ognlClass, false);
        return "<if test=\"_parameter != null\"><where>\n " +
                "${@" + className + "@andNotLogicDelete(_parameter)}" +
                "<trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n " +
                " <foreach collection=\"oredCriteria\" item=\"criteria\">\n  " +
                "  <if test=\"criteria.valid\">\n   " +
                "   ${@tk.mybatis.mapper.util.OGNL@andOr(criteria)}  " +
                "    <trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n " +
                "       <foreach collection=\"criteria.criteria\" item=\"criterion\">\n   " +
                "       <choose>\n            <when test=\"criterion.noValue\">\n   " +
                "           ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n " +
                "           </when>\n            <when test=\"criterion.singleValue\">\n   " +
                "           ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} " +
                "#{criterion.value}\n            </when>\n  " +
                "          <when test=\"criterion.betweenValue\">\n   " +
                "           ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)}" +
                " ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n " +
                "           </when>\n" +
                "            <when test=\"criterion.listValue\">\n " +
                "             ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n " +
                "             <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n" +
                "                #{listItem}\n " +
                "             </foreach>\n " +
                "           </when>\n       " +
                "   </choose>\n       " +
                " </foreach>\n     " +
                " </trim>\n    </if>\n " +
                " </foreach>\n </trim>\n</where></if>";
    }

    public static String selectJoinType(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        //查找表关联宁段
        Field[] declaredFields = entityClass.getDeclaredFields();
        JoinType annotation = entityClass.getAnnotation(JoinType.class);
        Table tableAno = entityClass.getAnnotation(Table.class);
        String curTbableName = "";
        if (tableAno != null) {
            curTbableName = tableAno.name();
        }
        if (annotation != null) {
            String type = annotation.value();
            for (Field declaredField : declaredFields) {
                JoinColumn anoJoinColumn = declaredField.getAnnotation(JoinColumn.class);
                JoinKey joinKey = declaredField.getAnnotation(JoinKey.class);
                String selfJoinName = null;
                if (joinKey != null) {
                    selfJoinName = joinKey.value();
                }
                if (anoJoinColumn != null) {
                    //默认表会指定
                    String[] table = anoJoinColumn.table();
                    String[] alias = anoJoinColumn.alias();
                    String[] column = anoJoinColumn.column();
                    String[] joinType = anoJoinColumn.joinType();
                    String curName = declaredField.getName();
                    for (int i = 0; i < table.length; i++) {
                        //有自定义类型使用自定义
                        if (joinType.length == table.length) {
                            if (joinType.length== 1 && "".equals(joinType[0])) {
                                sql.append(" ").append(type).append(" join");
                            } else {
                                String selfType = joinType[i].toLowerCase();
                                sql.append(" ").append(selfType).append(" join");
                            }
                        } else {
                            sql.append(" ").append(type).append(" join ");
                        }
                        //如果关联查询是一个@JoinKey注解的字段,此时要使用别名
                        if (selfJoinName != null) {
                            sql.append(table[i] + " " + alias[i] + " on " + selfJoinName + "."
                                    + StringUtil.camelhumpToUnderline(curName) + "=" + alias[i] + "." + column[i] + " ");
                        } else {
                            sql.append(table[i] + " " + alias[i] + " on " + curTbableName + "."
                                    + StringUtil.camelhumpToUnderline(curName) + "=" + alias[i] + "." + column[i] + " ");
                        }
                    }
                }
            }
        }
        return sql.toString();
    }
}
     
