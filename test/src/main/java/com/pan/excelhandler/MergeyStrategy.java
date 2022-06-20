package com.pan.excelhandler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.Serializable;
import java.util.List;

@Data
public class MergeyStrategy implements CellWriteHandler {

    /**
    *需要合并数据坐标
     * */
    List<RowRangeVo> rowRangeVos ;
    public static final String MERGE_ROW = "row" ;
    public static final String MERGE_COLUMN = "column";

    public MergeyStrategy(List<RowRangeVo> rowRangevos){ this.rowRangeVos = rowRangeVos; }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RowRangeVo implements Serializable {

        private static final long serialVersionUID = -990174142570902728L;
        /**
        水开始合并行、列号,不包含beginIndex此行
         */
        private int beginIndex ;
        /**
        *结束合并行、列号,包含endIndex此行
         */
        private int endIndex ;
        /**
        *针对哪行、列进行合并
         */
        private int targetIndex;
        /**
        *合并类型
         * */
        private String mergeType;

    }
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
        //当前行
        int curRowIndex = cell.getRowIndex();//当前列
        int curColIndex = cell.getColumnIndex();
        if (CollectionUtils.isEmpty(rowRangeVos)) {
            return;
        }
        for (RowRangeVo rowRangevo : rowRangeVos) {
            switch (rowRangevo.getMergeType()) {
                case MERGE_ROW:
                    if (curRowIndex > rowRangevo.getBeginIndex() && curRowIndex <= rowRangevo.getEndIndex()) {
                        if (curColIndex == rowRangevo.getTargetIndex()) {
                            mergeWithPrevRow(writeSheetHolder, curRowIndex, curColIndex);
                        }
                    }
                    break;
                case MERGE_COLUMN:
                    if (curColIndex > rowRangevo.getBeginIndex() && curColIndex <= rowRangevo.getEndIndex()) {
                        if (curRowIndex == rowRangevo.getTargetIndex()) {
                            mergeWithPrevColumn(writeSheetHolder, curRowIndex, curColIndex);
                        }
                    }
                    break;
            }
        }
    }

    private void mergeWithPrevRow (WriteSheetHolder writeSheetHolder,int curRowIndex, int curColIndex){
        Sheet sheet = writeSheetHolder.getSheet();
        List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
        boolean isMerged = false;
        for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
                CellRangeAddress cellRangeAddr = mergeRegions.get(i);
                //若上一个单元格已经被合并,则先移出原有的合并单元,再重新添加合并单元
                if (cellRangeAddr.isInRange(curRowIndex - 1, curColIndex)) {
                    sheet.removeMergedRegion(i);
                    cellRangeAddr.setLastRow(curRowIndex);
                    sheet.addMergedRegion(cellRangeAddr);
                    isMerged = true;
                } 
        }
//若上一个单元格未被合并,则新增合并单元
        if (!isMerged) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex - 1, curRowIndex, curColIndex,
                    curColIndex);
            sheet.addMergedRegion(cellRangeAddress);
        } 
    }

    private void mergeWithPrevColumn(WriteSheetHolder writeSheetHolder,int curRowIndex,int curColIndex) {
        Sheet sheet = writeSheetHolder.getSheet();
        List<CellRangeAddress> mergeRegions = sheet.getMergedRegions();
        boolean isMerged = false;
        for (int i = 0; i < mergeRegions.size() && !isMerged; i++) {
            CellRangeAddress cellRangeAddr = mergeRegions.get(i);
            //若上一个单元格已经被合并,则先移出原有的合并单元,再重新添加合并单元
            if (cellRangeAddr.isInRange(curRowIndex, curColIndex - 1)) {
                sheet.removeMergedRegion(i);
                cellRangeAddr.setLastColumn(curColIndex);
                sheet.addMergedRegion(cellRangeAddr);
                isMerged = true;
            }
        }
        //若上一个单元格未被合并,则新增合并单元
        if (!isMerged) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(curRowIndex, curRowIndex, curColIndex - 1,
                    curColIndex);
            sheet.addMergedRegion(cellRangeAddress);
        }
    }
}
