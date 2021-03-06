package com.xjm.hello.zookeeper.curator.typicalscenarios.lock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 没有加锁的情况下，出现并发问题
 */
public class Recipes_NoLock {

	public static void main(String[] args) throws Exception {
		final CountDownLatch down = new CountDownLatch(1);
		for(int i = 0; i < 10; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						down.await();
					} catch ( Exception e ) {
					}
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
					String orderNo = sdf.format(new Date());
					System.err.println("生成的订单号是 : "+orderNo);
				}
			}).start();
		}
		down.countDown();
	}
}