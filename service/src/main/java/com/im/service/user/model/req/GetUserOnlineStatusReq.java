package com.im.service.user.model.req;

import com.im.common.model.RequestBase;
import lombok.Data;

import java.util.List;

@Data
public class GetUserOnlineStatusReq extends RequestBase {

    private List<String> userList;

}