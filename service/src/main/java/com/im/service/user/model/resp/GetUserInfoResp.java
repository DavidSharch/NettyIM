package com.im.service.user.model.resp;

import com.im.service.user.dao.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class GetUserInfoResp {

    private List<UserEntity> userDataItem;

    private List<String> failUser;

}