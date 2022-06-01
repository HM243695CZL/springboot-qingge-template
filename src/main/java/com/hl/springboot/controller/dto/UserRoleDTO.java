package com.hl.springboot.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户关联角色的参数
 */
@Data
public class UserRoleDTO {

    private Integer userId;
    private List<Integer> roleIds;
}
