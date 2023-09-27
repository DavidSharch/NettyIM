package com.im.service.user.service;

import com.im.service.user.dao.UserEntity;
import com.im.service.user.model.req.*;
import com.im.service.user.model.resp.GetUserInfoResp;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.ResponseEntity;

/**
 * @Author : sharch
 * @create 2023/9/21 18:20
 */
public interface UserService {

     ResponseEntity importUser(ImportUserReq req);

     ResponseEntity deleteUser(DeleteUserReq req);

     ResponseEntity<GetUserInfoResp> getUserInfo(GetUserInfoReq req);

     ResponseEntity modifyUserInfo(ModifyUserInfoReq req);

     Boolean login(LoginReq req);

}
