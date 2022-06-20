package com.pan.tk.provider;

import com.pan.tk.helper.TkSqlHelper;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.Set;

public class TkMapperProvider extends MapperTemplate {
    public TkMapperProvider(Class<?> mapperClass,MapperHelper mapperHelper){
        super(mapperClass,mapperHelper) ;
    }
    public String selectSingleColumn (MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct ${_parameter} from" + TkSqlHelper.getTableName(entityClass) + " ");
        sql.append("<where>");
        sql.append(TkSqlHelper.getTkLogicDelete(entityClass));
        sql.append("</where>");
        return sql.toString();
    }
    public String selectTkCountByExample(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder("SELECT ");
        if (isCheckExampleEntityClass()) {
            sql.append(SqlHelper.exampleCheck(entityClass));
        }
        sql.append(SqlHelper.exampleCountColumn(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        sql.append(TkSqlHelper.selectJoinType(entityClass));
        sql.append(TkSqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleForUpdate());
        return sql.toString();
    }

    public String selectSingleNoneNullColumn (MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct $ {_parameter} from" +
                TkSqlHelper.getTableName(entityClass) + " where ${_parameter} is not null");
        sql.append(TkSqlHelper.getTkLogicDelete(entityClass));
        return sql.toString();
    }
    public String selectSeq(MappedStatement ms) {
        //修改返回值类型为实体类型
        StringBuilder sql = new StringBuilder () ;
        sql.append("select ${_parameter}.nextval from dual ");
        return sql.toString();
    }

    public String selectJoin(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(TkSqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        //添加关联表连接sql
        sql.append(TkSqlHelper.selectJoinType(entityClass));
        sql.append(TkSqlHelper.whereAllIfColumns(entityClass, isNotEmpty(), false));
        sql.append(TkSqlHelper.orderByDefault(entityClass));
        return sql.toString();
    }
    public String selectByJoinPrimaryKey (MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(TkSqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, tableName(entityClass)));
        //添加关联表连接sql
        sql.append(TkSqlHelper.selectJoinType(entityClass));
        sql.append(TkSqlHelper.wherePKColumns(entityClass, (String) null, false));
        return sql.toString();
    }
    public String selectByJoinExample(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms,entityClass) ;
        StringBuilder sql = new StringBuilder("SELECT ");
        if (isCheckExampleEntityClass()) {
            sql.append (SqlHelper.exampleCheck(entityClass)) ;
        }
        sql.append("<if test=\"distinct\">distinct</if>");
        //支持查询指定列
        sql.append(TkSqlHelper.exampleSelectColumns(entityClass)) ;
        sql.append(SqlHelper.fromTable(entityClass,tableName(entityClass))) ;
        //添加关联表连接sql
        sql.append(TkSqlHelper.selectJoinType(entityClass)) ;
        sql.append(TkSqlHelper. exampleWhereClause() );
        sql.append(TkSqlHelper.exampleOrderBy(entityClass)) ;
        sql.append(SqlHelper.exampleForUpdate());
        return sql.toString() ;
    }
    public String selectByJoinExampleAndRowBounds (MappedStatement ms) { return selectByJoinExample(ms) ; }
    public String updateBatch(MappedStatement statement) {
        //1.创建StringBuilder用于拼接SQL语句的各个组成部分
        StringBuilder builder = new StringBuilder();
        //2.拼接foreach标签
        builder.append("<foreach collection=\"list\" item=\"record\" separator=\";\">");
        //3.获取实体类对应的Class对象
        Class<?> entityClass = super.getEntityClass(statement);
        //4.获取实体类在数据库中对应的表名
        String tableName = super.tableName(entityClass);
        //5.生成update子句
        String updateClause = SqlHelper.updateTable(entityClass, tableName);
        builder.append(updateClause);
        builder.append("<set>");
        //6.获取所有字段信息
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        String idColumn = null;
        String idHolder = null;
        for (EntityColumn entityColumn : columns) {
            boolean isPrimaryKey = entityColumn.isId();
            //7.判断当前字段是否为主键
            if (isPrimaryKey) {
                //8.缓存主键的宁段名和宁段值
                idColumn = entityColumn.getColumn();
                //※返回格式如: #(record.age, jdbcType=NUMERIC,typeHandler=MyTypeHandler}
                idHolder = entityColumn.getColumnHolder("record");
            } else {
                //9.使用非主键字段拼接SET子句
                String column = entityColumn.getColumn();
                String columnHolder = entityColumn.getColumnHolder("record");
                builder.append(column).append("=").append(columnHolder).append(",");
            }
        }
        builder.append("</set>");
        //10.使用前面缓存的主键名、主键值拼接where子句
        builder.append("where ").append(idColumn).append("=").append(idHolder);
        builder.append("</foreach>");
        //11.将拼接好的字符串返回
        return builder.toString();
    }
    public String insertBatch(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        //oracle的话这个语句还需要额外调整
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\">");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();
        while (var5.hasNext()) {
            EntityColumn column = (EntityColumn) var5.next();
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }
 }
