package com.im.service.config;

import com.im.common.router.RouteHandle;
import com.im.common.router.hash.AbstractConsistentHash;
import com.im.common.router.hash.ConsistentHashHandle;
import com.im.common.router.hash.TreeMapConsistentHash;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * @Author : sharch
 * @create 2023/9/27 21:20
 */
public class BeanConfig {

    @Bean
    public RouteHandle routeHandle() throws Exception {
        ConsistentHashHandle hash = new ConsistentHashHandle();
        TreeMapConsistentHash treeHash = new TreeMapConsistentHash();
        hash.setHash(treeHash);
        return hash;
    }
}
