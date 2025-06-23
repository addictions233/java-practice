package com.one.threadpool;

import com.one.threadpool.service.AsyncThreadPoolService;
import com.one.threadpool.service.AsyncThreadPoolDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试异步线程池ThreadPool的三种使用方式
 */
@SpringBootTest
class ThreadPoolTest {
	@Autowired
	private AsyncThreadPoolDemo asyncThreadPoolDemo;

	@Autowired
	@Qualifier("threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private AsyncThreadPoolService asyncService;

	@Test
	void testExecute() throws InterruptedException {
		System.out.println("0" + Thread.currentThread());
		MyThread myThread = new MyThread();
		threadPoolTaskExecutor.execute(myThread);
		Thread.sleep(1000);
		myThread.start();
	}


	static class MyThread extends Thread {
		public MyThread() {
			System.out.println("1" + Thread.currentThread());
		}

		@Override
		public void run() {
			System.out.println("2" + Thread.currentThread());
		}
	}

	/**
	 * 1,测试异步线程池的第一中使用方式: 使用@Async注解修饰类中的方法, 那么该方法将由线程异步执行
	 */
	@Test
	void testAsyncThreadPool01() {
		System.out.println(Thread.currentThread().getName()+":主线程启动了");
		asyncThreadPoolDemo.printNumber();
		System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
	}

	/**
	 * 2,测试异步线程池的第二种使用方式: 使用异步线程池对象executor直接执行异步任务,
	 * 		有不带异步方法返回结果的execute()方法
	 * 		和带异步方法返回结果的submit()方法
	 */
	@Test
	void testAsyncThreadPool02() throws ExecutionException, InterruptedException {
		System.out.println(Thread.currentThread().getName()+":主线程启动了");
		String str = "aaa";
		// execute()方法就是提交线程任务来执行,不能获取异步方法的返回结果
		threadPoolTaskExecutor.execute(()->{
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName()+"----"+ i+ str );
			}
		});

		// submit()方法可以获取异步线程方法执行的结果和抛出的异常
		Future<String> future = threadPoolTaskExecutor.submit(() -> {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "-----" + i);
			}
			return "带返回结果的异步线程执行结束了";
		});
		System.out.println(future.get());

		System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
	}

	/**
	 * 3,测试异步线程池的第三种使用方式:使用 @Async注解修饰类, 那么该类就是异步类,
	 * 该类中的所有方法将会异步执行
	 */
	@Test
	public void testAsyncThreadPool03() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+":主线程启动了");
		asyncService.say("hello");
		asyncService.printNumber(10);
		System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
	}

	/**
	 * 4,测试异步线程中,能够再调用异步线程
	 */
	@Test
	public void testAsyncThreadPool04() {
		asyncService.say("hello");
	}

}
