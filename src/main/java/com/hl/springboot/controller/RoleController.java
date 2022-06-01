package com.hl.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.controller.dto.RoleMenuDTO;
import com.hl.springboot.entity.IdEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.common.Result;

import com.hl.springboot.service.IRoleService;
import com.hl.springboot.entity.Role;

import org.springframework.web.bind.annotation.RestController;

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

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
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
        return Result.success(roleService.list());
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

}

