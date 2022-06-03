package com.hl.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.springboot.controller.dto.CourseInfoDTO;
import com.hl.springboot.controller.dto.UserDTO;
import com.hl.springboot.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-27
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);


    Page<User> findPage(Page<User> page);

    List<CourseInfoDTO> getCourseInfo(Integer id);

    List<CourseInfoDTO> getStudentCourseInfo(Integer id);

    void setUserRole(User user);

    List<String> getRoleNameByUserId(Integer userId);

    List<Integer> getRoleIdByUserId(String id);
}
