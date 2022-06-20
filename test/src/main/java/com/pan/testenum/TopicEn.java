package com.pan.testenum;

public enum TopicEn {
    Test("pan");

    public final String name;

    TopicEn(String name) {
        this.name = name;
    }

    public static void print(){
        for (TopicEn topicEn : values()) {
            System.out.println(topicEn);
        }
    }
}
