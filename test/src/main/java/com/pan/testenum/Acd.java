package com.pan.testenum;

import java.util.List;

public class Acd {
    static{
        List<String> list= ActivityCondition.list;
        for (int i = 0; i < 10000; i++) {
            list.add(i+"pan");
        }
    }
}
