package com.pan.test;

public class Sql {
    //创建序列
    public static String createSeq(String seqName) {
        String sql = "--创建序列"+ seqName +"\n";
        sql = sql + "CREATE SEQUENCE SEQ_" + seqName + "\n";
        sql = sql + "INCREMENT BY 1\n";
        sql = sql + " START WITH 1\n";
        sql = sql + " MAXVALUE 1000000000 ; \n";
        System.out.println(sql);
        return sql;
    }

    public static void main(String[] args) {
        createSeq("TB_HERO");
    }
}
