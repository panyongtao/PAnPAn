package com.example.sbean;

import com.ejlchina.searcher.bean.*;
import com.example.service.CommonSearcherBean;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 实体类表的指定可以有多种形式，最简单的形式仅仅只是一个普通类也可以使用
 * 如下演示的是最基本的一种
 * 1.@SearchBean(tables = "user u inner join role r on u.role_id = r.id")
 * 2.@SearchBean(tables = "user u left join user_detail d on u.id = d.user_id") 左连接
 * 3.@SearchBean(tables = "user_detail d right join user u on u.id = d.user_id") 右连接
 * 4.From子查询@SearchBean(tables = "(select id, name from user) t")
 * 5.关联From子查询
 *@SearchBean(
 *     tables = "user u, (select user_id, ... from ...) t",
 *     joinCond = "u.id = t.user_id")
 * 6.字段的子查询
 * 该学生的总分数（Select 子查询）
 * @DbField("select sum(sc.score) from student_course sc where sc.student_id = s.id")
 * private int totalScore;
 * 7.where子查询
 * @SearchBean(
 *     tables = "student s",
 *     // 只查询考试均分及格的学生（Where 子查询）
 *     joinCond = "(select avg(sc.score) from student_course sc where sc.student_id = s.id) >= 60"
 * )
 * 8.Distinct去重
 * @SearchBean(
 *     tables = "student_course sc, course c",
 *     joinCond = "sc.course_id = c.id",
 *     distinct = true                     // 去重
 * )
 * 9.Group By 分组 与 聚合函数
 * @SearchBean(
 *     tables = "student_course sc",
 *     groupBy = "sc.course_id"
 * )
 * public class CourseScore {
 *     @DbField("sc.course_id")
 *     private long courseId;
 *     @DbField("sum(sc.score)")
 *     private long totalScore;
 *     // ...
 * }
 * 10.字段别名
 * @DbField(value = "DATE_FORMAT(u.date_created, '%Y-%m-%d')", alias="date")
 *     private String dateCreated;
 * 11.排序
 * @SearchBean(
 *     orderBy = "age desc",               // 如果该字段未指定，则表示：禁用排序
 *     sortType = SortType.ONLY_ENTITY     // 指定只有实体类中的排序信息才会生效，检索参数中的排序信息将会忽略
 * )
 * 12.参数嵌入 普通内嵌参数和拼接参数
 * @SearchBean(
 *     tables = "(select id, name from user where age = :age) t"   // 参数 age 的值由检索时动态指定
 * )
 * @SearchBean(
 *     tables = ":table:"      // 参数 table 由检索时动态指定，这在分表检索时非常有用
 * )
 * @SearchBean(
 *     tables = "student",
 *     joinCond = "age = :age"
 * )
 * @SearchBean(
 *     tables = "student",
 *     joinCond = "age in (:ages:)"    // 参数 ages 形如："18,20,25"
 * )
 * @SearchBean(
 *     tables = "student",
 *     groupBy = ":groupBy:"           // 动态指定分组条件
 * )
 * @DbField(":field:")
 *     private String value;	动态指定检索字段
 *
 * 实体类之间也是可以继承
 * 基类
 * public class BaseEntity {
 *     private long id;
 *     private long version;
 *     private Date createAt;
 *     private Date updateAt;
 * }
 * public class User extends BaseEntity {
 *     // 父类与子类的字段映射到同一张表
 *     private long id;
 *     private String username;
 *     private int roleId;
 * }
 * 表继承
 * @SearchBean(tables="user u, role r", joinCond="u.role_id = r.id")
 * public class User {
 *     private long id;
 *     private String username;
 *     private int roleId;
 *     @DbField("r.name")
 *     private String roleName;
 * }
 * // 将复用父类的 @SearchBean 注解
 * public class UserDetail extends User {
 *     private int age;
 *     private int status;
 *     @DbField("r.role_type")
 *     private int roleType;
 * }
 * 注意
 * 一个实体类只会有一个 @SearchBean 注解生效，如果子类和父类都添加了该注解，
 * 则子类的注解生效，父类的注解将被覆盖
 *
 * 属性忽略
 * 1.被关键字 static 或 transient 修饰的属性会被自动忽略
 * 2.@DbIgnore 忽略单个字段
 * 3.@SearchBean.ignoreFields 忽略多个字段
 * @SearchBean(
 *     ignoreFields = {"field1", "field2"}
 * )
 * 既然可以用 `@DbIgnore` 直接忽略指定字段，为什么还需要 `@SearchBean.ignoreFields` 呢？
 *
 * 原因一：在某些框架中，可能会在运行时对实体类动态添加某些字段，对于这些在运行时动态添加上去的字段，我们无法给它标记 @DbIgnore 注解
 * 原因二：有时候要忽略的属性在父类中，但这个属性在其它的子实体类中又不能被忽略
 * 4.全局属性忽略
 * bean-searcher.sql.default-mapping.ignore-fields	需要全局忽略的属性名（可指定多个）	字符串数组
 *
 * 用于指定该字段只允许接受的运算符，为空时，表示任意运算符都接受
 * 此时只需要传递对应的字段，后端就默认把此字段当作大于来处理，避免前端抱怨
 * 这么多查询条件变成了前端开发
 * @DbField(onlyOn = GreaterThan.class)
 */

@SearchBean(
		tables = "employee e, department d",
		joinCond = "e.department_id = d.id",
		autoMapTo = "e"							// 字段没使用 DbField 注解时，自动映射到 employee 表
)
@Data
public class EmployeeSearcherBean extends CommonSearcherBean implements BeanAware, ParamAware { // 这两接口 都是可选的
	// 自动映射到 "e.id"
	private Long id;

	@DbField
	private String name;

	// 自动映射到 "e.age"
	private Integer age;

	@DbField("d.name")
	private String department;
	// 自动映射到 "e.entry_date"
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private LocalDateTime entryDate;
	// 该字段不会映射到数据表
	@DbIgnore
	private String ignoreField = "ignore";
	/**
	 * BeanAware 接口的方法
	 */
	@Override
	public void afterAssembly() {
//		System.out.println("id = " + id + ", name = " + name + ", age = " + age + ", ignoreField = " + ignoreField);
	}

	/**
	 * ParamAware 接口的方法
	 */
	@Override
	public void afterAssembly(Map<String, Object> paraMap) {
//		System.out.println("paraMap = " + paraMap);
	}

}
