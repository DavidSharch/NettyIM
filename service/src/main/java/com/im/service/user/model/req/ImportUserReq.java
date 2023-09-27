package com.im.service.user.model.req;

import com.im.common.model.RequestBase;
import com.im.service.user.dao.UserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class ImportUserReq extends RequestBase {
    @ApiModelProperty("用户信息")
    private List<UserEntity> userData;
}
