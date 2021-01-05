package com.zzw;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @Author: Acgoto
 * @Date: 2020/11/19 21:37
 */
public class DistributeServer {
    private static String connectString = "192.168.211.146:2181,192.168.211.146:2182,192.168.211.146:2183";
    private static int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    private String parentNode = "/servers";

    // 创建到 zk 的客户端连接
    public void getConnect() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
    }

    // 注册服务器
    public void registerServer(String hostname) throws Exception {
        String create = zkClient.create(parentNode + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + " is online " + create);
    }

    // 业务功能
    public void business(String hostname) throws Exception {
        System.out.println(hostname + " is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        // 1、获取 zk 连接
        DistributeServer server = new DistributeServer();
        server.getConnect();
        // 2、利用 zk 连接注册服务器信息
        server.registerServer(args[0]);
        // 3、启动业务功能
        server.business(args[0]);
    }
}