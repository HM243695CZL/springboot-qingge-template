package com.hl.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hl.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from sys_user")
//    List<User> findAll();
//
//    @Insert("insert into sys_user(username, password, nickname, email, phone, address) " +
//            "values(#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address})")
//    Integer save(User user);
//
//    Integer update(User user);
//
//    @Delete("delete from sys_user where id = #{id}")
//    Integer deleteById(@Param("id") Integer id);
//
//    @Select("select * from sys_user where username like #{username} limit #{pageNum}, #{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String username);
//
//    @Select("select count(*) from sys_user where username like #{username}")
//    Integer selectTotal(String username);
}
