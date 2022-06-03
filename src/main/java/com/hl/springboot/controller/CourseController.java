package com.hl.springboot.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.common.Result;
import com.hl.springboot.entity.Course;
import com.hl.springboot.entity.IdEntity;
import com.hl.springboot.service.ICourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody Course course) {
        return Result.success(courseService.saveOrUpdate(course));
    }

    // 删除
    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        return Result.success( courseService.removeById(param.getId()));
    }

    // 批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(courseService.removeByIds(ids));
    }

    // 获取全部
    @GetMapping("/list")
    public Result list() {
        return Result.success(courseService.list());
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        return Result.success(courseService.getById(param.getId()));
    }

    // 分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                       @RequestParam Integer pageSize,
                        @RequestParam(defaultValue = "") String name) {
        Page<Course> page = courseService.findPage(new Page<>(pageIndex, pageSize), name);
        return Result.success(page);
    }

}

