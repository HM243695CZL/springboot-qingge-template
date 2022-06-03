package com.hl.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.springboot.controller.dto.StudentCourseDTO;
import com.hl.springboot.entity.Course;
import com.hl.springboot.mapper.CourseMapper;
import com.hl.springboot.service.ICourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-06-02
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public Page<Course> findPage(Page<Course> page, String name) {
        return courseMapper.findPage(page, name);
    }


    @Transactional
    @Override
    public void setStudentCourse(StudentCourseDTO studentCourseDTO) {
        courseMapper.deleteStudentCourse(studentCourseDTO.getCourseId(), studentCourseDTO.getStudentId());
        courseMapper.setStudentCourse(studentCourseDTO.getCourseId(), studentCourseDTO.getStudentId());
    }
}
