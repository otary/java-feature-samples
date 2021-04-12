package cn.chenzw.java.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 泡茶流程
 * <ul>
 * <li>（1）主线程（MainThread）的工作是：启动清洗线程、启动烧水线程，等清洗、烧水的工作完成后，泡茶喝。</li>
 * <li>（2）清洗线程（WashThread）的工作是：洗茶壶、洗茶杯。</li>
 * <li>（3）烧水线程（HotWarterThread）的工作是：洗好水壶，灌上凉水，放在火上，一直等水烧开。</li>
 * </ul>
 */
@RunWith(JUnit4.class)
public class MakeTeaTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 使用Join阻塞合并
     */
    @Test
    public void testJoin() {
        HotWarterThread hotWarterThread = new HotWarterThread();
        WashThread washThread = new WashThread();

        hotWarterThread.start();
        washThread.start();

        try {
            // 等待烧水和清洗完毕才往下执行
            hotWarterThread.join();
            washThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 等待上述2个步骤执行完后再执行
        logger.info("泡茶喝");
    }

    /**
     * 烧水线程
     */
    public static class HotWarterThread extends Thread {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        public HotWarterThread() {
            super("烧水线程-Thread");
        }

        @Override
        public void run() {
            try {
                logger.info("洗好水壶");
                logger.info("灌上凉水");
                logger.info("放在火上");

                Thread.sleep(500);

                logger.info("水开了");
            } catch (InterruptedException e) {
                logger.error("发生异常中断", e);
            }
            logger.info("运行结束");
        }
    }

    /**
     * 清洗线程
     */
    public static class WashThread extends Thread {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void run() {
            try {
                logger.info("洗茶壶");
                logger.info("洗茶杯");
                logger.info("拿茶叶");

                Thread.sleep(500);

                logger.info("洗完了");
            } catch (InterruptedException e) {
                logger.error("发生异常中断", e);
            }
            logger.info("运行结束");
        }
    }

    // --------------------------------------

    /**
     * 使用FutureTask示例
     */
    @Test
    public void testFutureTask() {
        FutureTask<Boolean> hotWaterFutureTask = new FutureTask<>(new HotWaterJob());
        Thread hotWaterThread = new Thread(hotWaterFutureTask, "烧水-Thread");

        FutureTask<Boolean> washFutureTask = new FutureTask<>(new WashJob());
        Thread washThread = new Thread(washFutureTask, "清洗-Thread");

        hotWaterThread.start();
        washThread.start();

        try {
            Boolean hotWaterResult = hotWaterFutureTask.get();
            Boolean washResult = washFutureTask.get();

            logger.info("烧水结果:" + hotWaterResult);
            logger.info("清洗结果:" + washResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static class HotWaterJob implements Callable<Boolean> {
        private Logger logger = LoggerFactory.getLogger(getClass());

        public Boolean call() throws Exception {

            try {
                logger.info("洗好水壶");
                logger.info("灌上凉水");
                logger.info("放在火山");

                Thread.sleep(1000);

                logger.info("水开了");
            } catch (InterruptedException e) {
                logger.error("发生中断");
                return false;
            }

            return true;
        }
    }

    public static class WashJob implements Callable<Boolean> {
        private Logger logger = LoggerFactory.getLogger(getClass());

        public Boolean call() throws Exception {

            try {
                logger.info("洗茶壶");
                logger.info("洗茶杯");
                logger.info("拿茶叶");

                Thread.sleep(1000);

                logger.info("洗完了");
            } catch (InterruptedException e) {
                logger.error("清洗工作发生中断");
                return false;
            }

            return true;
        }
    }


}
