package com.im.service.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.im.service.user.dao.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : sharch
 * @create 2023/9/21 18:23
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    int updateUserDelFlagByIds(@Param("ids") List<String> userIds);
}
