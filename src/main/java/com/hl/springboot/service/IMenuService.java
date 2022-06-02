package com.hl.springboot.service;

import com.hl.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-31
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String title);
}
