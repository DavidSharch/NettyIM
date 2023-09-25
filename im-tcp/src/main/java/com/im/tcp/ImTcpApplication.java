package com.im.tcp;

import com.im.tcp.config.IMServerConfig;
import com.im.tcp.server.ImServer;
import com.im.tcp.server.WebSocketServer;
import io.netty.bootstrap.BootstrapConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;


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
        }catch (Exception e){
            e.printStackTrace();
            System.exit(500);
        }
    }
}
