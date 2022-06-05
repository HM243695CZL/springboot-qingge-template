package com.hl.springboot.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.common.Constants;
import com.hl.springboot.common.Result;
import com.hl.springboot.controller.dto.RoleMenuDTO;
import com.hl.springboot.entity.IdEntity;
import com.hl.springboot.entity.Role;
import com.hl.springboot.entity.RoleMenu;
import com.hl.springboot.entity.UserRole;
import com.hl.springboot.mapper.RoleMenuMapper;
import com.hl.springboot.mapper.UserRoleMapper;
import com.hl.springboot.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody Role role) {
        removeRedis(Constants.ROLE_KEY);
        return Result.success(roleService.saveOrUpdate(role));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        // 删除用户角色关联表和角色菜单关联表的数据
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", param.getId());
        userRoleMapper.delete(queryWrapper);

        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", param.getId());
        roleMenuMapper.delete(wrapper);
        removeRedis(Constants.ROLE_KEY);
        return Result.success( roleService.removeById(param.getId()));
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeByIds(ids));
    }

    // 获取全部
    @GetMapping("/list")
    public Result list() {
        // 从缓存中获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.ROLE_KEY);
        List<Role> roles;
        if (StrUtil.isBlank(jsonStr)) {
            roles = roleService.list();
            // 将数据缓存到redis
            stringRedisTemplate.opsForValue().set(Constants.ROLE_KEY, JSONUtil.toJsonStr(roles));
        } else {
            roles = JSONUtil.toBean(jsonStr, new TypeReference<List<Role>>() {
            }, true);
        }
        return Result.success(roles);
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        return Result.success(roleService.getById(param.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String name
                       ) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageIndex, pageSize), queryWrapper));
    }

    /**
     * 分配角色和菜单的关系
     * @param roleMenuDTO
     * @return
     */
    @PostMapping("/roleMenu")
    public Result roleMenu(@RequestBody RoleMenuDTO roleMenuDTO) {
        roleService.setRoleMenu(roleMenuDTO);
        return Result.success();
    }

    /**
     * 获取角色对应的菜单
     */
    @GetMapping("/getRoleMenu")
    public Result getRole2Menu(@RequestParam Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }

    /**
     * 删除redis缓存
     * @param key
     */
    private void removeRedis(String key) {
        stringRedisTemplate.delete(key);
    }

}

