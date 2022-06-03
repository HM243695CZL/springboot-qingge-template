package com.hl.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.springboot.common.Constants;
import com.hl.springboot.controller.dto.CourseInfoDTO;
import com.hl.springboot.controller.dto.UserDTO;
import com.hl.springboot.entity.Menu;
import com.hl.springboot.entity.User;
import com.hl.springboot.entity.UserRole;
import com.hl.springboot.exception.ServiceException;
import com.hl.springboot.mapper.CourseMapper;
import com.hl.springboot.mapper.RoleMenuMapper;
import com.hl.springboot.mapper.UserMapper;
import com.hl.springboot.mapper.UserRoleMapper;
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
    private UserRoleMapper userRoleMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtils.copyProperties(one, userDTO);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

//            Integer roles = one.getRoleId();
//            // 设置用户的菜单列表
//            List<Menu> roleMenus = getRoleMenus(roles);
//            userDTO.setMenuList(roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public Page<User> findPage(Page<User> page) {
        return userMapper.findPage(page);
    }

    @Override
    public List<CourseInfoDTO> getCourseInfo(Integer id) {
        return courseMapper.getCourseInfo(id);
    }

    @Override
    public List<CourseInfoDTO> getStudentCourseInfo(Integer id) {
        return courseMapper.getStudentCourseInfo(id);
    }

    /**
     * 分配用户和角色的关系
     * @param user
     */
    @Override
    public void setUserRole(User user) {
        // 先删除当前用户的所有角色
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        userRoleMapper.delete(queryWrapper);
        // 给当前用户绑定角色
        for (Integer roleId : user.getRoleIds()) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    /**
     * 根据用户id获取角色名称
     * @param userId 用户id
     * @return
     */
    @Override
    public List<String> getRoleNameByUserId(Integer userId) {
        return userMapper.getRoleNameByUserId(userId);
    }

    /**
     * 根据用户id获取角色id
     * @param id
     * @return
     */
    @Override
    public List<Integer> getRoleIdByUserId(String id) {
        return userMapper.getRoleIdByUserId(id);
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
