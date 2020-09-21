package cn.chenzw.java7.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class SemaphoreTests {

    private static Logger logger = LoggerFactory.getLogger(SemaphoreTests.class);

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 50, 60, TimeUnit.SECONDS,
            new LinkedBlockingDeque(500), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 10个人做3个任务（同一时间一个任务只能由一个人做）
     */
    @Test
    public void test() throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(new Job(semaphore, i));
        }

        while (threadPoolExecutor.getActiveCount() > 0) {
            Thread.sleep(1000);
        }

    }


    /**
     * 任务
     */
    public static class Job implements Runnable {

        private Semaphore semaphore;

        private int i;

        public Job(Semaphore semaphore, int i) {
            this.semaphore = semaphore;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();

                logger.info("工人[{}]开始做任务！", i);
                Thread.sleep(2000);
                logger.info("工人[{}]做完任务！", i);

                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
