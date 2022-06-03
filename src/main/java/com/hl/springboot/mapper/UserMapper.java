package com.hl.springboot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-27
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page);

    List<String> getRoleNameByUserId(@Param("userId") Integer userId);

    List<Integer> getRoleIdByUserId(@Param("id") String id);
}
