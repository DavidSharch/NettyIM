package com.im.service.user.model.req;

import com.im.common.model.RequestBase;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginReq extends RequestBase {

    @NotEmpty(message = "用户id不能位空")
    private String userId;

}