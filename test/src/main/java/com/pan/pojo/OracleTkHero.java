package com.pan.pojo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.pan.mapper.OracleHeroTkMapper;
import com.pan.tk.BeanManual;
import com.pan.tk.annotion.JoinType;
import com.pan.tk.annotion.PageInit;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "tb_hero")  //3.给通用mapper找表用
@JoinType
@PageInit
@ColumnWidth(15)
@HeadStyle(borderBottom = BorderStyle.MEDIUM, borderRight = BorderStyle.MEDIUM,fillForegroundColor = 44)
@HeadFontStyle(fontHeightInPoints = 12,bold = false)
@HeadRowHeight(20)
@ExcelIgnoreUnannotated
public class OracleTkHero extends BeanManual<OracleHeroTkMapper,OracleTkHero> implements Serializable {
    @Id  //主
    @KeySql(sql = "select SBQ_FORM_REQUIRBMBNT_ID.nextval from dual",order=ORDER.BEFORE)
    private Integer id;
    private String username;
    private String profession;
    @Transient  //不扫描此字段
    private String phone;
    private String email;
    private Date onlinetime;
}
