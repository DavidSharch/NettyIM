package com.im.service.user.service;

import com.im.service.user.model.req.DeleteUserReq;
import com.im.service.user.model.req.ImportUserReq;
import org.springframework.http.ResponseEntity;

/**
 * @Author : sharch
 * @create 2023/9/21 18:20
 */
public interface UserService {

     ResponseEntity importUser(ImportUserReq req);

     ResponseEntity deleteUser(DeleteUserReq req);
//
//     ResponseEntity<GetUserInfoResp> getUserInfo(GetUserInfoReq req);
//
//     ResponseEntity<ImUserDataEntity> getSingleUserInfo(String userId , Integer appId);
//
//
//     ResponseEntity modifyUserInfo(ModifyUserInfoReq req);
//
//     ResponseEntity login(LoginReq req);
//
//    ResponseEntity getUserSequence(GetUserSequenceReq req);
}
