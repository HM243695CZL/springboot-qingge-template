package com.hl.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.entity.IdEntity;
import com.hl.springboot.entity.User;
import com.hl.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list () {
        return userService.list();
    }


    @GetMapping("/page")
    public IPage<User> findPage (
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "") String username
            ) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.orderByDesc("id");
        return userService.page(page, queryWrapper);
    }

    @PostMapping("/create")
    public boolean save(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/view")
    public Object view(@RequestBody IdEntity param) {
        return userService.getById(param.getId());
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody IdEntity param) {
        return userService.removeById(param.getId());
    }

    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }
}
