package com.hl.springboot.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuMetaDTO {

    private String title;
    private String isLink;
    private Integer isHide;
    private Integer isKeepAlive;
    private Integer isAffix;
    private Integer isIframe;
    private List<String> roles;
    private String icon;
}
