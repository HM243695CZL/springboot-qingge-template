package com.hl.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.springboot.common.Constants;
import com.hl.springboot.controller.dto.UserDTO;
import com.hl.springboot.entity.Menu;
import com.hl.springboot.entity.User;
import com.hl.springboot.exception.ServiceException;
import com.hl.springboot.mapper.RoleMenuMapper;
import com.hl.springboot.mapper.UserMapper;
import com.hl.springboot.service.IMenuService;
import com.hl.springboot.service.IUserService;
import com.hl.springboot.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RoleMenuMapper roleMenuMapper;


    @Resource
    private IMenuService menuService;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtils.copyProperties(one, userDTO);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            Integer roles = one.getRoles();
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(roles);
            userDTO.setMenuList(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    /**
     * 获取当前角色的菜单列表
     * @param roles
     * @return
     */
    private List<Menu> getRoleMenus(Integer roles) {
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roles);
        // 获取所有菜单
        List<Menu> menus = menuService.findMenus("");
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // 移除children里面不在menuIds集合中的元素
            children.removeIf(child ->
                    !menuIds.contains(child.getId())
            );
        }
        return roleMenus;
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
