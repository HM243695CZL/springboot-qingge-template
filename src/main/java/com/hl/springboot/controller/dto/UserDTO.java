package com.hl.springboot.controller.dto;

import com.hl.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接收前端登录请求的参数
 */
@Data
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String token;
    private Integer roles;
    private List<Menu> menuList;
}
