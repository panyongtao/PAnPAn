package com.pan.testenum;

import java.util.List;

public class Abc {
    static{
       List<String> list= ActivityCondition.list;
        for (int i = 0; i < 10000; i++) {
            list.add(i+"pan");
        }
    }

    public static void main(String[] args) {
        new Acd();
        System.out.println(ActivityCondition.list.size());
    }
}
