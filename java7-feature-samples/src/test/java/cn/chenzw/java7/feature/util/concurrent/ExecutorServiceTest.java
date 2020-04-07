package cn.chenzw.java7.feature.util.concurrent;

import cn.chenzw.java7.feature.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

        final AtomicInteger count = new AtomicInteger(1);
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

    List list = Collections.synchronizedList(new ArrayList<User>());

    /**
     * SynchronizedList
     *
     * @throws InterruptedException
     */
    @Test
    public void testSynchronizedList() throws InterruptedException {

        // 定时将list中的数据写出
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (list.size() > 10) {
                        List<User> newList = new ArrayList<>();
                        Collections.addAll(newList, new User[list.size()]);
                        Collections.copy(newList, list);
                        list = Collections.synchronizedList(new ArrayList<User>());

                        logger.info("{}", newList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);


        // 不断往列表中写入数据
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        final AtomicInteger count = new AtomicInteger(1);
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100000; j++) {
                        User user = new User();
                        int n = count.getAndIncrement();
                        user.setId((long) n);
                        user.setName("n" + n);
                        list.add(user);

                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        while (!scheduledExecutorService.isTerminated()) {
            TimeUnit.SECONDS.sleep(1);
        }
    }



}
