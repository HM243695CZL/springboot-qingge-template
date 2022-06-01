package com.hl.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.entity.IdEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.common.Result;

import com.hl.springboot.service.IUserRoleService;
import com.hl.springboot.entity.UserRole;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-06-01
 */
@RestController
@RequestMapping("/user-role")
public class UserRoleController {

    @Resource
    private IUserRoleService userRoleService;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody UserRole userRole) {
        return Result.success(userRoleService.saveOrUpdate(userRole));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        return Result.success( userRoleService.removeById(param.getId()));
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userRoleService.removeByIds(ids));
    }

    // 获取全部
    @GetMapping("/list")
    public Result list() {
        return Result.success(userRoleService.list());
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        return Result.success(userRoleService.getById(param.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                       @RequestParam Integer pageSize) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(userRoleService.page(new Page<>(pageIndex, pageSize), queryWrapper));
    }

}

