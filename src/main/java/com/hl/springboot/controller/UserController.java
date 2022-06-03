package com.hl.springboot.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.common.Constants;
import com.hl.springboot.common.Result;
import com.hl.springboot.controller.dto.CourseInfoDTO;
import com.hl.springboot.controller.dto.UserDTO;
import com.hl.springboot.controller.dto.UserRoleDTO;
import com.hl.springboot.entity.IdEntity;
import com.hl.springboot.entity.Role;
import com.hl.springboot.entity.User;
import com.hl.springboot.entity.UserRole;
import com.hl.springboot.mapper.UserRoleMapper;
import com.hl.springboot.service.IUserService;
import com.hl.springboot.utils.TokenUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 新增或者更新
    @PostMapping("/create")
    public Result save(@RequestBody User user) {
        boolean b = userService.saveOrUpdate(user);
        userService.setUserRole(user);
        return Result.success(b);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody IdEntity param) {
        return Result.success( userService.removeById(param.getId()));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }

    @GetMapping("/list")
    public Result list() {
        return Result.success(userService.list());
    }

    /**
     * 根据角色id获取对应的所有用户
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/getUsersByRoleId")
    public Result getUsersByRoleId (@RequestParam Integer roleId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return Result.success(userService.list(queryWrapper));
    }

    @PostMapping("/view")
    public Result findOne(@RequestBody IdEntity param) {
        List<Integer> roleIdsList = userService.getRoleIdByUserId(param.getId());
        User user = userService.getById(param.getId());
        user.setRoleIds(roleIdsList);
        return Result.success(user);
    }

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageIndex,
                        @RequestParam Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println("========");
//        System.out.println(currentUser.getUsername());
//        System.out.println("========");
//       Page<User> page =  userService.findPage(new Page<>(pageIndex, pageSize));
        Page<User> page = userService.page(new Page<>(pageIndex, pageSize), queryWrapper);
        for (User user : page.getRecords()) {
            List<String> roleNameList = userService.getRoleNameByUserId(user.getId());
            user.setRoles(roleNameList);
        }
        return Result.success(page);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有数据
        List<User> list = userService.list();
        // 通过工具类创建writer写出到磁盘路径
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 一次性写出list内的对象到Excel，使用默认样式，强制输出标题
        writer.write(list, true);
        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        return Result.success(true);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("userInfo", userService.getById(dto.getId()));
        map.put("token", dto.getToken());
        map.put("menuList", dto.getMenuList());
        return Result.success(map);
    }

    @PostMapping("/userInfo")
    public Result getUserInfo(@RequestBody IdEntity param) {
        return Result.success(userService.getById(param.getId()));
    }

    /**
     * 根据老师id获取老师用户对应的课程信息
     * @param id 老师的id
     * @return
     */
    @GetMapping("/getCourseInfo")
    public Result getCourseInfo(@RequestParam Integer id) {
        return Result.success(userService.getCourseInfo(id));
    }

    /**
     * 根据学生id获取学生已选课程
     * @param id 学生id
     * @return
     */
    @GetMapping("/getStudentCourseInfo")
    public Result getStudentCourseInfo(@RequestParam Integer id) {
        return Result.success(userService.getStudentCourseInfo(id));
    }

}

