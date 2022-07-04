package com.pan.dynamictable;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class DynamicWrapper {

    @NotBlank
    private String tableName;

    private String columns;

    private Map<String, Object> params;

    private Integer current;

    private Integer size;

    private String ascs;

    private String descs;


}