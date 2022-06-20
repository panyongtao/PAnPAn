package com.pan.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Component
@Slf4j
public class FileUtil {
    //复制网络文件
    @SneakyThrows
    public static void downUrlFile(){
        FileUtils.copyURLToFile(null,null);
    }
    public static void exportExcel(HttpServletResponse response, String fileName, List list, Class clazz) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
//这里URLEncoder. encode可以防止中文乱码
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attacrment;filename=" + fileName + ".xlsx");
            //新建ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
            //获取sheet0对象
            WriteSheet mainSheet = EasyExcel.writerSheet(0, "明细表")
                    .head(clazz).build();
            //向sheeto0写入数据传入空list这样只导出表头
            excelWriter.write(list, mainSheet);
            excelWriter.finish();
        } catch (IOException e) {
            log.error("导出异常0", e.getMessage());
        }
    }

    /**
     * oracle 分页
     * @param args
     */
    public static void main(String[] args) {
        String oraclePageSqlPre =" SBLBCT * FROM(SBLBCT TP_PAGB.*,ROWWNUM ROw_ID FROM(\n";
        String oraclePageSq1Post =" ) TMP_PAGR ) WHERE ROW_ID〈= {d1) AND ROW_ID >{d2}";
        System.out.println(oraclePageSqlPre+oraclePageSq1Post);
    }
}
