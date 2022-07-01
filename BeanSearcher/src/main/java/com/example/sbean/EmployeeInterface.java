package com.example.sbean;

import com.ejlchina.searcher.bean.*;
import com.example.service.SearcheInterface;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SearchBean(
		tables = "employee e, department d",
		joinCond = "e.department_id = d.id",
		autoMapTo = "e"							// 字段没使用 DbField 注解时，自动映射到 employee 表
)
@Data
public class EmployeeInterface implements BeanAware, ParamAware,SearcheInterface { // 这两接口 都是可选的
	@JsonIgnore
	@DbIgnore
	//核心传参，方便bean类通过内部属性params来传参
	protected Map<String, Object> params=new HashMap<>();
	@JsonIgnore
	@DbIgnore
	//统计字段list
	protected List<String> summerFieldList;
	@JsonIgnore
	@DbIgnore
	//单个统计字段
	protected String summeryField;
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
