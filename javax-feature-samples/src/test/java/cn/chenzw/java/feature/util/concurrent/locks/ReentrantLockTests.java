package cn.chenzw.java.feature.util.concurrent.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@RunWith(JUnit4.class)
public class ReentrantLockTests {

    /**
     * 快速轮询
     */
    @Test
    public void testTryLock() {
        Lock lock = new ReentrantLock();

        // 加锁放在try..catch外，避免加锁异常时释放锁错误
        if (lock.tryLock()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    @Test
    public void testFair() throws InterruptedException {
        // 默认使用NonfairSync（非公平锁）
        ReentrantLock lock = new ReentrantLock(true);

        for (int i = 0; i < 10; i++) {
            int tid = i;
            new Thread(() -> {
                try {
                    if (lock.tryLock(5, TimeUnit.SECONDS)) {
                        log.info("线程{}获取到锁!", tid);

                        Thread.sleep(1000);

                        lock.unlock();
                    } else {
                        log.info("线程{}未获取到锁!", tid);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }



}
