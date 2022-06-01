package com.hl.springboot.service;

import com.hl.springboot.controller.dto.RoleMenuDTO;
import com.hl.springboot.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-31
 */
public interface IRoleService extends IService<Role> {

    void setRoleMenu(RoleMenuDTO roleMenuDTO);

    List<Integer> getRoleMenu(Integer roleId);
}
