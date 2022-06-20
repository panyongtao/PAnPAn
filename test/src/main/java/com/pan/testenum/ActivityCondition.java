package com.pan.testenum;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActivityCondition {

   public static List<String> list=new ArrayList<>();

   //待办生成条件
   public static List<InnerCondtion> conditionList=new ArrayList<>();
   //初始化添加条件
   static{
   }
   public static boolean ifHandler(String result,String businessNo){
      if(StringUtils.isBlank(result)){
         return false;
      }
      for (InnerCondtion condition : conditionList) {
         String startPrefix = condition.getStartPrefix();
         WorkflowBusinessResultEnums resultEnums = condition.getWorkflowBusinessResultEnums();
         boolean flag;
         if(StringUtils.isNotBlank(startPrefix) && StringUtils.isNotBlank(businessNo)){
            flag=businessNo.startsWith(startPrefix) && resultEnums.getCode().equals(result);
         }else{
            flag=resultEnums.getCode().equals(result);
         }
         if (flag) {
            return flag;
         }
      }
      return false;
   }

   @Data
   @Builder
   public static class InnerCondtion{
      //单号前缀
      private String startPrefix;
      private WorkflowBusinessResultEnums workflowBusinessResultEnums;
   }
}
