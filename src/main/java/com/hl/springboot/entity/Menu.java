package com.hl.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-31
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("菜单名称")
      private String title;

      @ApiModelProperty("组件名称")
      private String name;

      @ApiModelProperty("路由路径")
      private String path;

      @ApiModelProperty("组件路径")
      private String component;

      @ApiModelProperty("是否超链接菜单")
      private String isLink;

      @ApiModelProperty("是否隐藏 0：隐藏 1: 显示")
      private Boolean isHide;

      @ApiModelProperty("是否缓存 0: 否 1：是")
      private Boolean isKeepAlive;

      @ApiModelProperty("是否固定标签 0：否 1：是")
      private Boolean isAffix;

      @ApiModelProperty("是否内嵌窗口 0：否 1：是")
      private Boolean isIframe;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("父级id")
      private Integer pid;

      @TableField(exist = false)
      private List<Menu> children;

      @TableField(exist = false)
      private List<String> roles;
}
