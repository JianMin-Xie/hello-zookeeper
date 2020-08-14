package com.xjm.hello.zookeeper.session;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 创建一个基本的 ZooKeeper 对象实例，复用 sessionId 和 session passwd
 *
 * @author JianMin Xie
 */
public class ZooKeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper("192.168.102.140:2181",
                5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD());
        connectedSemaphore.await();
        long sessionId = zookeeper.getSessionId();
        byte[] passwd = zookeeper.getSessionPasswd();

        zookeeper = new ZooKeeper("192.168.102.140:2181",
                5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD(),
                1L,
                "test".getBytes());
        zookeeper = new ZooKeeper("192.168.102.140:2181",
                5000,
                new ZooKeeper_Constructor_Usage_With_SID_PASSWD(),
                sessionId,
                passwd);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event：" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }
}