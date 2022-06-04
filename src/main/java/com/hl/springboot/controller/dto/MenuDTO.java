package com.hl.springboot.controller.dto;

import lombok.Data;

@Data
public class MenuDTO {

    private Integer id;
    private String path;
    private String name;
    private String component;
    private MenuMetaDTO meta;
    private MenuDTO children;
}