package com.hl.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.controller.dto.RoleMenuDTO;
import com.hl.springboot.entity.Menu;
import com.hl.springboot.entity.Role;
import com.hl.springboot.entity.RoleMenu;
import com.hl.springboot.mapper.RoleMapper;
import com.hl.springboot.mapper.RoleMenuMapper;
import com.hl.springboot.service.IMenuService;
import com.hl.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private IMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(RoleMenuDTO roleMenuDTO) {
        // 先删除当前角色id所有的绑定关系
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleMenuDTO.getRoleId());
        roleMenuMapper.delete(queryWrapper);
        // 将前端传过来的菜单id数组绑定到当前角色去
        ArrayList<Integer> menuIdsCopy = CollUtil.newArrayList(roleMenuDTO.getMenuIds());
        for (Integer menuId : roleMenuDTO.getMenuIds()) {
            Menu menu = menuService.getById(menuId);
//            // 二级菜单  并且 传过来的menuId数组没有它的父id
//            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) {
//                RoleMenu roleMenu = new RoleMenu();
//                roleMenu.setRoleId(roleMenuDTO.getRoleId());
//                roleMenu.setMenuId(menu.getPid());
//                roleMenuMapper.insert(roleMenu);
//                menuIdsCopy.add(menu.getPid());
//            }
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
