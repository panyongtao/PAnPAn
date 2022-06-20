package com.pan.tk;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.pan.tk.annotion.*;
import com.pan.tk.constants.LikeConstant;
import com.pan.tk.helper.TkSqlHelper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;
import tk.mybatis.mapper.weekend.reflection.Reflections;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 带join的都是关联查询
 */
@Data
@Slf4j
public abstract class BeanManual <D extends TkMapper<T>,T extends BeanManual>{
    private static final long serialVersionUID = -4986681654713627294L;
    @Transient
    @JsonIgnore
    protected D mapper;
    protected List<T> list;
    @Transient
    @JsonIgnore
    protected boolean queryOneOrMany = false;
    @Transient
    @JsonIgnore
    private Class<T>modeClass;
    @Transient
    protected Integer pageNo;
    @Transient
    protected Integer pageSize;
    public BeanManual() {
        //获取泛型类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modeClass = (Class<T>) pt.getActualTypeArguments()[1];
        mapper = SpringUtil.getBean((Class<D>) pt.getActualTypeArguments()[0]);
    }
    public String selectSeq(String seq){ return mapper.selectSeq(seq); }
    public T selectById(){
        T t = mapper.selectByJoinPrimaryKey(getPrimaryFieldvalue((T) this));
        queryOneOrMany(t);
        return t;
    }

    public List<String> selectSingleColumn(String column){ return mapper.selectSingleColumn(column); }

    public List<String> selectSingleColumn(Fn<T, Object> fn){
        String column = Reflections.fnToFieldName(fn);
        column = StringUtil.camelhumpToUnderline(column);
        return mapper. selectSingleColumn(column);
    }

    public List<String> selectSingleNoneColumn(String column){
        return mapper.selectSingleNoneNullColumn(column);
    }
    public List<String> selectSingleNoneColumn(Fn<T,Object> fn){
        String column = Reflections.fnToFieldName(fn);
        column = StringUtil.camelhumpToUnderline(column) ;
        return mapper.selectSingleNoneNullColumn(column);
    }

    @SneakyThrows
    private Object getPrimaryFieldvalue(T t) {
        Field[] fields = modeClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                field.setAccessible(true);
                return field.get(t);
            }
        }
        return null;
    }


    private void queryOneOrMany (T t){
        if (t == null) {
            return;
        }
        if (this.queryOneOrMany){
            hanleOneOrMany(t);
        }
    }

    @SneakyThrows
    public List<T>selectListByField(String fieldName){
        List<T> ts = mapper.selectJoin(getFieldValue((T) this, fieldName));
        handleLoopOneOrMany(ts);
        return ts;
    }

    @SneakyThrows
    public List<T> selectListByField(Fn<T,Object> fn){
        String field = Reflections. fnToFieldName(fn);
        List<T> ts = this.selectListByField(field) ;
        handleLoopOneOrMany(ts);
        return ts;
    }

    @SneakyThrows
    public int save(){
        Field field = getLogicDeleteField() ;
        if (field != null){
            field.setAccessible(true) ;
            field.set(this,"0");
        }
        return mapper.insertSelective((T) this) ;
    }
    public void insertBatch(List<T> list){
        mapper.insertBatch(list);
    }

    public int update(String field) {
        return mapper.updateByPrimaryKeySelective(getUpdateFieldValue((T) this, field));
    }
    public int update(Fn<T,Object> fn) {
        String field = Reflections.fnToFieldName(fn);
        return update(field);
    }

    public int update() { return mapper. updateByPrimaryKeySelective((T) this); }
    public void updateBatch(List<T> list){ mapper.updateBatch(list); }
    public int delete() {
        T t = (T) this;
        Field field = getLogicDeleteField();
        if (field != null) {
            setLogicDeleteValueByField(field, t);
            return  mapper.updateByPrimaryKeySelective(t);
        }
        return mapper.delete(t);
    }

    @Data
    @NoArgsConstructor
    private class InnerPage {
        Integer pageNo;
        Integer pageSize;
        int offset;
    }
    private InnerPage handlePage(T t) {
        Integer pageNo = t.pageNo;
        Integer pageSize = t.pageSize;
        if (pageNo == null || pageSize == null) {
            //处理分页注解
            PageInit annotation = modeClass.getAnnotation(PageInit.class);
            if (annotation != null) {
                pageNo = annotation.pageNo();
                pageSize = annotation.pageSize();
            }
        }
        //仍然为null时给个默认值
        pageNo = pageNo == null ? 1 : pageNo;
        pageSize = pageSize == null ? 20 : pageSize;//设置分页
        int offset = pageSize * (pageNo - 1);
        InnerPage page = new InnerPage();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setOffset(offset);
        return page;
    }

    private Weekend<T> handleWeekend(T t){
        Weekend<T> weekend = new Weekend<> (modeClass);
        WeekendCriteria<T, Object> criteria = weekend. weekendCriteria() ;//给列加个前缀
        addPrefix(weekend) ;//获取所有分页条件
        t.handleCritera(criteria);
        //若当前条件仍有不满足的在这里设置好条件
        criteriaAfterHandle(criteria) ;
        weekendAfterHandle(weekend) ;//排序
        order(weekend) ;
        return weekend;
    }
    protected void criteriaAfterHandle(WeekendCriteria<T,Object> criteria){
    }
    protected void weekendAfterHandle(Weekend<T> weekend){
    }
    protected void order(Weekend<T> weekend){
        Field[] declaredFields = modeClass.getDeclaredFields() ;
        for (Field field : declaredFields){
            Order annotation = field. getAnnotation(Order.class) ;
            if (annotation != null){
                String value = annotation. value() ;
                if (value.equals("ASC")) {
                    weekend.orderBy(field.getName()).asc();
                }
                if (value.equals("DESC")) {
                    weekend.orderBy(field.getName()).desc();
                }
            }
        }
    }

    @SneakyThrows
    private void addPrefix(Weekend weekend) {
        Map<String, EntityColumn> propertyMap = weekend.getPropertyMap();
        String curTableDot = TkSqlHelper.getTableNameDot(modeClass);
        Set<Map.Entry<String, EntityColumn>> entrySet = propertyMap.entrySet();
        for (Map.Entry<String, EntityColumn> entry : entrySet) {
            EntityColumn value = entry.getValue();
            if (value != null) {
                //已经包含.的话跳过
                String column = value.getColumn();
                if (column != null && column.contains(".")) {
                    continue;
                }
                value.setColumn(curTableDot + value.getColumn());
            }
        }
        Field[] fields = modeClass.getDeclaredFields();
        for (Field field : fields) {
            JoinKey annotation = field.getAnnotation(JoinKey.class);
            Transient trano = field.getAnnotation(Transient.class);
            if (annotation != null && trano != null) {
                String targetTable = annotation.value();
                if (!"".equals(targetTable)) {
                    targetTable = targetTable + ".";
                    EntityColumn entityColumn = propertyMap.get(field.getName());
                    if (entityColumn != null) {
                        String entityColumnColumn = entityColumn.getColumn();
                        if (entityColumnColumn != null && entityColumnColumn.contains(".")) {
                            continue;
                        }
                    }
                    EntityColumn column = new EntityColumn();
                    EntityField entityField = new EntityField(field, null);
                    column.setEntityField(entityField);
                    column.setColumn(targetTable + StringUtil.camelhumpToUnderline(field.getName()));
                    propertyMap.put(field.getName(), column);
                }
            }
        }
    }

    @SneakyThrows
    protected void handleCritera (WeekendCriteria<T, Object> criteria) {
        Class<WeekendCriteria> weekendCriteriaClass = WeekendCriteria.class;
        Set<String> methodNames = ReflectUtil.getMethodNames(weekendCriteriaClass);
        methodNames = methodNames.parallelStream().
                filter(v -> v.startsWith("and") || v.startsWith("or"))
                .collect(Collectors.toSet());//处理weekend
        Field[] declaredFields = modeClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Condition condition = field.getAnnotation(Condition.class);
            if (condition != null) {
                String sql = condition.sql();
                //获取分页查询条件值
                String conditionValue = condition.value();
                //获取当前对象属性值
                field.setAccessible(true);
                Object filedValue = field.get(this);
                //表字段属性
                String column = field.getName();
                if (field.getType() == String.class) {
                    String columnValue = (String) filedValue;
                    if (StringUtil.isNotEmpty(columnValue)) {
                        if (conditionValue.contains("Like")) {
                            String likeType = condition.likeType();
                            if ("".equals(likeType)) {
                                columnValue = "%" + columnValue + "%";
                            } else if (LikeConstant.LEFT.equals(likeType)) {
                                columnValue = "%" + columnValue;
                            } else if (LikeConstant.RIGHT.equals(likeType)) {
                                columnValue = columnValue + "%";
                            }
                        }
                        if (conditionValue.contains("Equal") || conditionValue.contains("Like")) {
                            ReflectUtil.invoke(criteria, conditionValue, column, columnValue);
                        }
                        if (conditionValue.contains("Null")) {
                            ReflectUtil.invoke(criteria, conditionValue, column);
                        }
                        //仅适用查询比较两个多值以","隔开只要有相同的就返回true
                        if (conditionValue.contains("Condition")) {
                            if (StringUtil.isNotEmpty(sql)) {
                                //替换{}字符串
                                ReflectUtil.invoke(criteria, conditionValue, StrUtil.format(sql, columnValue));
                            } else {
                                String columnProperty = "";
                                JoinKey annotation = field.getAnnotation(JoinKey.class);
                                if (annotation != null) {
                                    String value = annotation.value();
                                    if (StringUtils.isNotBlank(value)) {
                                        columnProperty = value + "." + StringUtil.camelhumpToUnderline(field.getName());
                                    } else {
                                        String curTableDot = TkSqlHelper.getTableNameDot(modeClass);
                                        columnProperty = curTableDot + StringUtil.camelhumpToUnderline(field.getName()) ;
                                        ReflectUtil.invoke(criteria,conditionValue,handlesql(columnProperty,columnValue));
                                    }
                                }
                            }
                        }
                    }
                }
                if (filedValue != null) {
                    if (field.getType() == Date.class || field.getType() == Integer.class
                            || field.getType() == Long.class || field.getType() == Float.class
                            || field.getType() == Double.class) {
                        if (conditionValue.contains("Than") || conditionValue.contains("Equal")) {
                            ReflectUtil.invoke(criteria, conditionValue, column, filedValue);
                        }
                        if (conditionValue.contains("Null")) {
                            ReflectUtil.invoke(criteria, conditionValue, column);
                        }
                        if (conditionValue.contains("Between")) {
                            String end = condition.end();
                            if (StringUtil.isNotEmpty(end)) {
                                Field endField = modeClass.getDeclaredField(end);
                                endField.setAccessible(true);
                                Date endValue = (Date) endField.get(this);
                                ReflectUtil.invoke(criteria, conditionValue, column, filedValue, endValue);
                            }
                        }
                    }
                    if (field.getType() == List.class) {
                        List<String> list = (List<String>) filedValue;
                        if (conditionValue.contains("In") && CollectionUtil.isNotEmpty(list)) {
                            String inColumn = condition.column();
                            if (!"".equals(inColumn)) {
                                ReflectUtil.invoke(criteria, conditionValue, inColumn, list);
                            }
                        }
                        //仅适用查询比较两个多值以"，"隔开只要有相同的就返回true
                        if (conditionValue.contains("Condition") && CollectionUtil.isNotEmpty(list)) {
                            if (StringUtil.isNotEmpty(sql)) {
                                ReflectUtil.invoke(criteria, conditionValue, sql);
                            } else {
                                //调整在list上加注解的情况
                                String compareColumn = condition.column();
                                if (StringUtils.isNotBlank(compareColumn)) {
                                    Field declaredField = modeClass.getDeclaredField(compareColumn);
                                    if (declaredField != null) {
                                        JoinKey annotation = declaredField.getAnnotation(JoinKey.class);
                                        if (annotation != null) {
                                            String value = annotation.value();
                                            if (StringUtils.isNotBlank(value)) {
                                                column = value + "." + StringUtil.camelhumpToUnderline(compareColumn);
                                            }
                                        } else {
                                            String curTableDot = TkSqlHelper.getTableNameDot(modeClass);
                                            column = curTableDot + StringUtil.camelhumpToUnderline(compareColumn);
                                        }
                                    }
                                }
                                ReflectUtil.invoke(criteria, conditionValue, handleListSql(column, list));
                            }
                        }
                    }
                }
            }
        }
    }
    //多条件判断是不是属于里面范围
    private String handlesql(String property,String value){
        List<String> list = handleStringList(value);
        return handleListSql(property, list) ;
    }
    public List<String>handleStringList(String querycondition) {
        String guerys[] = querycondition.split(",");
        return Arrays.asList(guerys);
    }


    //多条件判断是不是属于里面范围
    private String handleListSql(String property, List<String> list) {
        StringBuilder sqlCondition = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sqlCondition.append("(instr(concat(" + property + ",','),'" + list.get(i) + ",')> 0");
            } else {
                sqlCondition.append(" or instr(concat(" + property + ",','),'" + list.get(i) + ",')> 0");
            }
        }
        if (list.size() != 0) {
            sqlCondition.append(")");
        }
        return sqlCondition.toString();
    }


    @SneakyThrows
    public PageInfo<T> selectPage() {
        //内部分页实体
        InnerPage innerPage = handlePage((T) this) ;//分页对象
        RowBounds bounds = new RowBounds(innerPage.offset,innerPage.pageSize);//Example
        Weekend<T> weekend = handleWeekend((T) this);
        int pageTotal = mapper.selectTkCountByExample(weekend) ;
        List<T> foist = mapper.selectByJoinExampleAndRowBounds(weekend,bounds);//是否关联查询1对1或者1对多
        handleLoopOneOrMany(foist);
        return handlePageList(pageTotal,foist,innerPage);
    }

    @SneakyThrows
    public List<T> selectExampleList(){
        Weekend<T> weekend = handleWeekend((T) this);
        List<T> ts = mapper.selectByJoinExample(weekend) ;
        //是否关联查询1对1或者1对多
        handleLoopOneOrMany(ts);return ts;
    }

    public List<T>selectAll () {
        Weekend<T> weekend = handleAllWeekend();
        List<T> ts = mapper.selectByJoinExample(weekend);
        handleLoopOneOrMany(ts);
        return ts;
    }
    private Weekend<T>handleAllWeekend(){
        Weekend<T> weekend = new Weekend<>(modeClass);
        WeekendCriteria<T, Object> criteria = weekend.weekendCriteria();//给列加个前缀
        addPrefix(weekend);
        //若当前条件仍有不满足的在这里设置好条件criteriaAfterHandle(criteria);
        weekendAfterHandle(weekend);//排序
        order(weekend);
        return weekend;
    }

    private PageInfo<T> handlePageList(int pageTotal,List<T> fvolist,InnerPage innerPage) {
        Page<T> page = new Page<>();
        if (pageTotal != 0) {
            //查询列表之前数据处理，可在子类中操作
            for (T t : fvolist) {
                pageBeforHandle(t);
            }
            page.addAll(fvolist);
            page.setPageNum(innerPage.pageNo);
            page.setPageSize(innerPage.pageSize);
            page.setPages(pageTotal / innerPage.pageSize + 1);
            page.setTotal(pageTotal);
        }
        PageInfo<T> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }

    protected void pageBeforHandle(T t){}




    @SneakyThrows
    private T getUpdateFieldValue(T t, String fieldName){
        T model = modeClass.newInstance() ;
        Field[] fields = modeClass.getDeclaredFields() ;//设置主键
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                field.setAccessible(true);
                field.set(model, field.get(t)); break;
            }
        }
        //设置对应域的值
        Field field = modeClass.getDeclaredField(fieldName) ;
        field.setAccessible(true) ;
        field.set(model,field.get(t)) ;
        return model;
    }


        @SneakyThrows
    private T getFieldValue(T t, String fieldName) {
        T model = modeClass.newInstance() ;
        Field field = modeClass.getDeclaredField(fieldName) ;
        field.setAccessible(true);
        field.set(model,field.get(t));
        if (getLogicDeleteField() != null) {
            setLogicDeleteValueByField(getLogicDeleteField(), model);
        }
        return model ;
    }

    @SneakyThrows
    private void setLogicDeleteValueByField(Field field,T t){
        field.setAccessible(true);
        field.set(t,"1");
    }

    private Field getLogicDeleteField() {
        Field[] fields = modeClass.getDeclaredFields() ;
        for (Field field : fields) {
            if (field.getAnnotation(TkLogicDelete.class) != null
                    && field.getType() == String.class) {
                return field;
            }
        }
        return null;
    }


    private void handleLoopOneOrMany (List<T> ts){
        for (T t : ts){
            if (this.queryOneOrMany) {
                t.setQueryOneOrMany(true);
                queryOneOrMany(t);
            }
        }
    }
    public T selectByField(String fieldName){
        List<T> list = this.selectListByField(fieldName) ;
        if (list != null && list.size() >0){
            T t = list.get(0) ;
            if (this.queryOneOrMany){
                t.setQueryOneOrMany(true) ;queryOneOrMany(t);
            }
            return t ;
        }
        return null;
    }

    public T selectByField(Fn<T,Object> fn){
        String field = Reflections. fnToFieldName(fn) ;
        return selectByField(field);
    }



    @SneakyThrows
    private void hanleOneOrMany(T t) {
        Field[] fields = modeClass.getDeclaredFields();
        for (Field field : fields) {
            OneOrMany oneOrMany = field.getAnnotation(OneOrMany.class);
            if (oneOrMany != null) {
                //单纯对象
                Class targetClass = field.getType();//List的话
                if (targetClass == List.class) {
                    Type genericType = field.getGenericType();
                    if (genericType == null) continue;
                    //如果是泛型参数的类型
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象
                        targetClass = (Class<?>) pt.getActualTypeArguments()[0];
                    }
                }
                //获取本类目标字段
                String target = oneOrMany.target();
                //获取要查询目标类字段
                //获取要查询目标类字段
                String to = oneOrMany.to();//获取本类目标字段域
                Object oneValue = null;
                Field oneField = null;
                if (!target.equals("")) {
                    oneField = modeClass.getDeclaredField(target);
                    if (oneField != null) {
                        oneField.setAccessible(true);//获取当前类对应字段值
                        oneValue = oneField.get(t);
                    }
                }
                Class superclass = targetClass.getSuperclass();//判断父类是不是BeanManual
                if (superclass == BeanManual.class) {
                    if (oneValue == null) {
                        return;
                    }
                    //值为空的话直接返回
                    Class<?> fieldType = oneField.getType();
                    if (fieldType == String.class) {
                        if ("".equals(oneValue)) {
                            return;
                        }
                    }
                    BeanManual bean = (BeanManual) targetClass.newInstance();
                    Field toField = targetClass.getDeclaredField(to);
                    toField.setAccessible(true);
                    //设置目标类对象字段值
                    toField.set(bean, oneValue);
                    field.setAccessible(true);
                    if (field.getType() == List.class) {
                        field.set(t, bean.selectListByField(to));
                    } else {
                        field.set(t, bean.selectByField(to));
                    }
                } else {//如果使用常规mapper就使用如下
                    //获取目标mapper对象
                    Class mapper = oneOrMany.mapper();
                    Object targetMapper = SpringUtil.getBean(mapper);
                    String methodName = oneOrMany.method();
                    if (mapper != void.class && StringUtil.isNotEmpty(methodName)) {
                        Method targetMethod = null;
                        for (Method method : mapper.getMethods()) {
                            if (method.getName().equals(methodName)) {
                                targetMethod = method;
                                break;
                            }
                        }
                        if (targetMethod == null) {
                            log.error("{}类中未找到此方法{}", mapper.getName(), methodName);
                            return;
                        }
                        int parameterCount = targetMethod.getParameterCount();
                        if (parameterCount == 0) {
                            field.setAccessible(true);
                            field.set(t, ReflectUtil.invoke(targetMapper, targetMethod));
                        }
                        if (parameterCount == 1) {
                            if (oneValue == null)
                                return;
                        }
                        //值为空的话直接返回
                        Class<?> fieldType = oneField.getType();
                        if (fieldType == String.class && "".equals(oneValue)) {
                            return;
                        }
                        Class<?> parameter = oneOrMany.parameter();
                        //参数类型正好和字段类型相等
                        if (fieldType == parameter) {
                            field.setAccessible(true);
                            field.set(t, ReflectUtil.invoke(targetMapper, methodName, oneValue));
                        } else {//不相等时认为是使用对象传参
                            Field toField = parameter.getDeclaredField(to);
                            toField.setAccessible(true);
                            Object paramBean = parameter.newInstance();
                            toField.set(paramBean, oneValue);
                            field.setAccessible(true);
                            System.out.println(methodName);
                            field.set(t, ReflectUtil.invoke(targetMapper, methodName, paramBean));
                        }
                    }
                }
            }
        }
    }
}
