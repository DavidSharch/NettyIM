package com.im.tcp.register;

import com.im.common.enums.Constants;
import com.im.tcp.config.IMServerConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistryZK implements Runnable {

    private ZKit zKit;

    private String ip;

    private IMServerConfig.TcpConfig tcpConfig;

    public RegistryZK(ZKit zKit, String ip, IMServerConfig.TcpConfig tcpConfig) {
        this.zKit = zKit;
        this.ip = ip;
        this.tcpConfig = tcpConfig;
    }

    @Override
    public void run() {
        zKit.createRootNode();
        // /im-coreRoot/tcp/{ip}:{port}
        String tcpPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootTcp + "/" + ip + ":" + tcpConfig.getTcpPort();
        zKit.createNode(tcpPath);
        log.info("Registry zookeeper tcpPath success, msg=[{}]", tcpPath);

        // /im-coreRoot/web/{ip}:{port}
        String webPath = Constants.ImCoreZkRoot + Constants.ImCoreZkRootWeb + "/" + ip + ":" + tcpConfig.getWebSocketPort();
        zKit.createNode(webPath);
        log.info("Registry zookeeper webPath success, msg=[{}]", tcpPath);
    }
}
