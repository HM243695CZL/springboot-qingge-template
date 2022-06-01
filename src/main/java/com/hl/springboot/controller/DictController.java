package com.hl.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.entity.IdEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hl.springboot.common.Result;

import com.hl.springboot.service.IDictService;
import com.hl.springboot.entity.Dict;

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
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody Dict dict) {
        return Result.success(dictService.saveOrUpdate(dict));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        return Result.success( dictService.removeById(param.getId()));
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(dictService.removeByIds(ids));
    }

    // 获取全部
    @GetMapping("/list")
    public Result list() {
        return Result.success(dictService.list());
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        return Result.success(dictService.getById(param.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                       @RequestParam Integer pageSize) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        return Result.success(dictService.page(new Page<>(pageIndex, pageSize), queryWrapper));
    }

}

