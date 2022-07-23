package com.ok;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String gender;
    private String name;
    private String age;
    private Date time;
}
