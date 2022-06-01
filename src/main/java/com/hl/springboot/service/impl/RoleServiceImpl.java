package com.hl.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.controller.dto.RoleMenuDTO;
import com.hl.springboot.entity.Role;
import com.hl.springboot.entity.RoleMenu;
import com.hl.springboot.mapper.RoleMapper;
import com.hl.springboot.mapper.RoleMenuMapper;
import com.hl.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public void setRoleMenu(RoleMenuDTO roleMenuDTO) {
        // 先删除当前角色id所有的绑定关系
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleMenuDTO.getRoleId());
        roleMenuMapper.delete(queryWrapper);
        // 将前端传过来的菜单id数组绑定到当前角色去
        for (Integer menuId : roleMenuDTO.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleMenuDTO.getRoleId());
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
