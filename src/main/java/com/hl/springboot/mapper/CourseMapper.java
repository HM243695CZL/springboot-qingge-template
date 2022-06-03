package com.hl.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.controller.dto.CourseInfoDTO;
import com.hl.springboot.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-06-02
 */
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page, @Param("name") String name);

    List<CourseInfoDTO> getCourseInfo(@Param("id") Integer id);
}
