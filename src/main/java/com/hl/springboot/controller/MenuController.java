package com.hl.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.common.Constants;
import com.hl.springboot.entity.Dict;
import com.hl.springboot.entity.IdEntity;
import com.hl.springboot.entity.RoleMenu;
import com.hl.springboot.mapper.DictMapper;
import com.hl.springboot.mapper.RoleMenuMapper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.common.Result;

import com.hl.springboot.service.IMenuService;
import com.hl.springboot.entity.Menu;

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
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", param.getId());
        roleMenuMapper.delete(queryWrapper);

        return Result.success( menuService.removeById(param.getId()));
    }

    // 获取全部
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "") String title) {
        return Result.success( menuService.findMenus(title));
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        return Result.success(menuService.getById(param.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                       @RequestParam Integer pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageIndex, pageSize), queryWrapper));
    }

    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }

}

