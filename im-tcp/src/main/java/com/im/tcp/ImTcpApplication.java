package com.im.tcp;

import com.im.tcp.config.IMServerConfig;
import com.im.tcp.register.RegistryZK;
import com.im.tcp.register.ZKit;
import com.im.tcp.server.ImServer;
import com.im.tcp.server.WebSocketServer;
import com.im.tcp.utils.RedisManager;
import io.netty.bootstrap.BootstrapConfig;
import org.I0Itec.zkclient.ZkClient;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ImTcpApplication {
    private static String path = "D:\\code\\NettyIM\\im-tcp\\src\\main\\resources\\application.yml";

    public static void main(String[] args) {
        if(args.length > 0){
            // 可以在启动时指定配置文件
            path = args[0];
        }
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(path);
            IMServerConfig config = yaml.loadAs(inputStream, IMServerConfig.class);
            new ImServer(config.getIMServer()).start();
            new WebSocketServer(config.getIMServer()).start();

            //MqFactory.init(config.getIMServer().getRabbitmq());
            //MessageReciver.init(config.getIMServer().getBrokerId()+"");
            //RedisManager.init(config);
            registerZK(config);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(500);
        }
    }

    public static void registerZK(IMServerConfig config) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        ZkClient zkClient = new ZkClient(
                config.getIMServer().getZkConfig().getZkAddr(),
                config.getIMServer().getZkConfig().getZkConnectTimeOut()
        );
        ZKit zKit = new ZKit(zkClient);
        RegistryZK registryZK = new RegistryZK(zKit, hostAddress, config.getIMServer());
        Thread thread = new Thread(registryZK);
        thread.start();
    }
}
