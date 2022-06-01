package com.hl.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.springboot.controller.dto.UserDTO;
import com.hl.springboot.entity.User;

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
}
