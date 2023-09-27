package com.im.service.user.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author : sharch
 * @create 2023/9/21 18:22
 */
@Data
@TableName("im_user_data")
public class UserEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private String userId;

    // 用户名称
    private String nickName;

    //位置
    private String location;

    //生日
    private String birthDay;

    // 密码
    private String password;

    // 头像url
    private String photo;

    // 性别
    private Integer userSex;

    // 个性签名
    private String selfSignature;

    @ApiModelProperty(value = "加好友验证类型,1需要验证", example = "1")
    private Integer friendAllowType;

    @ApiModelProperty(value = "管理员禁止用户添加加好友：0 未禁用 1 已禁用", example = "1")
    private Integer disableAddFriend;

    @ApiModelProperty(value = "禁用标识(0 未禁用 1 已禁用)", example = "1")
    private Integer forbiddenFlag;

    // 禁言标识
    private Integer silentFlag;

    @ApiModelProperty(value = "用户类型 1普通用户 2客服 3机器人", example = "1")
    private Integer userType;

    private Integer appId;

    private Integer delFlag;

    private String extra;
}
