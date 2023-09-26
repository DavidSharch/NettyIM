package com.im.common.enums;

/**
 * @Author : sharch
 * @create 2023/9/26 10:10
 */
public enum ConnectStatusEnum {

    /**
     * 管道链接状态,1=在线，2=离线。。
     */
    ONLINE_STATUS(1),

    OFFLINE_STATUS(2),
    ;

    private Integer code;

    ConnectStatusEnum(Integer code){
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
