package cn.chenzw.java7.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class ExecutorServiceTest {


    private Logger logger = LoggerFactory.getLogger(getClass());

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


}
