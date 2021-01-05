package com.zzw;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Acgoto
 * @Date: 2020/11/19 16:30
 */
public class ZookeeperTest {
    private static String connectString = "192.168.211.146:2181,192.168.211.146:2182,192.168.211.146:2183";
    private static int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    // @Test
    @Before
    public void init() throws Exception {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            // 收到事件通知后的回调函数（用户的业务逻辑）
            public void process(WatchedEvent event) {
                /*System.out.println("------------------start---------------------");
                System.out.println("-------" + event.getType() + "-------" + event.getPath());
                // 再次启动监听
                try {
                    List<String> children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println(child);
                    }
                    System.out.println("------------------end-------------------");
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    // 1、创建子节点
    @Test
    public void create() throws Exception {
        // 参数 1：要创建的节点的路径； 参数 2：节点数据（字节数组）； 参数 3：节点权限 ；参数 4：节点的类型
        String nodeCreated = zkClient.create("/zzw", "zhangsan".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(nodeCreated);
    }

    // 2、获取子节点
    @Test
    public void getChildren() throws Exception {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        // 延时阻塞使得上面的回调函数被执行
        Thread.sleep(Long.MAX_VALUE);
    }

    // 3、判断 zNode 是否存在
    @Test
    public void exist() throws Exception {
        Stat stat = zkClient.exists("/zhangsan", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }
}