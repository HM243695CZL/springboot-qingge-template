package com.hl.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-06-02
 */
public interface ICourseService extends IService<Course> {


    Page<Course> findPage(Page<Course> page, String name);
}
