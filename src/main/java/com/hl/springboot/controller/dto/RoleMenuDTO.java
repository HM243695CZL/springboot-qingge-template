package com.hl.springboot.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色关联菜单的参数
 */
@Data
public class RoleMenuDTO {

    private Integer roleId;
    private List<Integer> menuIds;
}
