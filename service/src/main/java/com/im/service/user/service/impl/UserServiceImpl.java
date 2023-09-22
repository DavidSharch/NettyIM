package com.im.service.user.service.impl;

import com.im.common.enums.UserErrorCode;
import com.im.service.user.dao.UserEntity;
import com.im.service.user.dao.mapper.UserMapper;
import com.im.service.user.model.req.*;
import com.im.service.user.model.resp.GetUserInfoResp;
import com.im.service.user.model.resp.ImportUserResp;
import com.im.service.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : sharch
 * @create 2023/9/21 18:20
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseEntity importUser(ImportUserReq req) {
        if (req.getUserData().size() > 100) {
            return ResponseEntity.badRequest().body(UserErrorCode.IMPORT_SIZE_BEYOND);
        }
        ImportUserResp resp = new ImportUserResp();
        List<String> successId = new ArrayList<>();
        List<String> errorId = new ArrayList<>();

        for (UserEntity data: req.getUserData()) {
            try {
                data.setAppId(req.getAppId());
                if (userMapper.insert(data) == 1) {
                    successId.add(data.getUserId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("保存用户失败，user info:{}", data);
                errorId.add(data.getUserId());
            }
        }
        resp.setErrorId(errorId);
        resp.setSuccessId(successId);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity deleteUser(DeleteUserReq req) {
        List<String> ids = req.getUserId();
        if (ids.size() == 0) {
            return ResponseEntity.badRequest().body("请求删除的user_id不能为空");
        }
        int update = userMapper.updateUserDelFlagByIds(ids);
        return ResponseEntity.ok(update);
    }

    @Override
    public ResponseEntity<GetUserInfoResp> getUserInfo(GetUserInfoReq req) {
        return null;
    }

    @Override
    public ResponseEntity modifyUserInfo(ModifyUserInfoReq req) {
        return null;
    }

    @Override
    public ResponseEntity login(LoginReq req) {
        return null;
    }
}
