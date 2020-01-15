package cn.chenzw.java7.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(JUnit4.class)
public class ExecutorServiceTest {


    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定时、延迟线程池
     *
     * @throws InterruptedException
     */
    @Test
    public void testScheduledExecutorService() throws InterruptedException {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        // 只执行一次
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("scheduled执行...");
            }
        }, 1, TimeUnit.SECONDS);

        // 固定频率执行
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("scheduleAtFixedRate执行...");
            }
        }, 0, 1, TimeUnit.SECONDS);


        while (!scheduledExecutorService.isTerminated()) {
            TimeUnit.SECONDS.sleep(1);
        }

    }

    /**
     * 定时执行（异常抛出）
     *
     * @throws InterruptedException
     */
    @Test
    public void testScheduledThreadPoolExecutorWithException() throws InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private final AtomicInteger threadId = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "my-schedule-thread-" + threadId.getAndIncrement());
                logger.info("Create new thread [{}]!", thread.getName());
                return thread;
            }
        });

        AtomicInteger count = new AtomicInteger(1);

        // 内部任务异常需要进行捕获处理，否则定时任务会退出
        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.info("Schedule [{}]", count.get());

                try {
                    if (count.getAndIncrement() % 5 == 0) {
                        throw new RuntimeException("抛出异常!");
                    }
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    logger.error("Schedule with exception!", e);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        while (!scheduledThreadPoolExecutor.isTerminated()) {
            TimeUnit.SECONDS.sleep(1);
        }
    }


}
