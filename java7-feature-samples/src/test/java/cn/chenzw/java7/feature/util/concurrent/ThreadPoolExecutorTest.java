package cn.chenzw.java7.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(JUnit4.class)
public class ThreadPoolExecutorTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 线程池最大支持20并发，超过则抛出RejectedExecutionException异常
     *
     * @throws InterruptedException
     */
    @Test(expected = RejectedExecutionException.class)
    public void testRejectedExecutionException() {
        // 自定义一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
            // 指定线程ID
            private final AtomicInteger threadId = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "test-thread-" + threadId.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        });

        // 线程池最大支持20并发，超过则抛出RejectedExecutionException异常
        for (int i = 0; i < 200; i++) {
            final int index = i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    logger.info("开始执行 {} 项工作!", index);
                }
            });
        }
    }

    @Test
    public void test() throws InterruptedException {

        // 自定义一个线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 20, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10), new ThreadFactory() {

            // 指定线程ID
            private final AtomicInteger threadId = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "test-thread-" + threadId.getAndIncrement());
            }
        });


        // 自定义拒绝策略
       /*
       threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                logger.info("ThreadPool [{}] reject thread [{}]!", executor.toString(), r.toString());
            }
        });
        */

        // 直接丢弃，不抛出异常
        // threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        // 丢弃，抛出RejectedExecutionException异常（默认）
        // threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        // 不丢弃，使用调用者线程执行（保证每个任务都能执行到）
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 丢弃最老的那个
        // threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 200; i++) {
            final int index = i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    count.incrementAndGet();
                    logger.info("开始执行 {} 项工作!", index);
                }
            });
        }

        while (threadPoolExecutor.getActiveCount() > 0) {
            Thread.sleep(2000);
        }

        logger.info("总执行数量: [{}]", count);
    }

}
