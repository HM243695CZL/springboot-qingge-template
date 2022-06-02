package com.hl.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hl243695czyn
 * @since 2022-05-27
 */
@Getter
@Setter
  @TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      @Alias("用户名")
      private String username;

      @ApiModelProperty("密码")
      @Alias("密码")
      @JsonIgnore
      private String password;

      @ApiModelProperty("昵称")
      @Alias("昵称")
      private String nickname;

      @ApiModelProperty("所属角色")
      @Alias("昵称")
      private Integer roles;

      @ApiModelProperty("邮箱")
      @Alias("邮箱")
      private String email;

      @ApiModelProperty("电话")
      @Alias("电话")
      private String phone;

      @ApiModelProperty("地址")
      @Alias("地址")
      private String address;

      @ApiModelProperty("创建时间")
      @Alias("创建时间")
      private LocalDateTime createTime;


}
