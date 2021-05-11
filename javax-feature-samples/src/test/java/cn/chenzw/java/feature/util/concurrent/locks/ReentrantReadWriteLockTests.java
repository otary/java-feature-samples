package cn.chenzw.java.feature.util.concurrent.locks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
@Slf4j
@RunWith(JUnit4.class)
public class ReentrantReadWriteLockTests {

    @Test
    public void test() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        for (int i = 0; i < 10; i++) {
            int tid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readLock.lock();
                    try {
                        // 进行读操作（读读不互斥 --  同一时间执行）
                        log.info("线程{}进行读操作...", tid);

                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock.unlock();
                    }
                }
            }).start();
        }


        for (int i = 0; i < 10; i++) {
            int tid = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writeLock.lock();
                    try {
                        // 进行写操作（写写互斥 -- 间隔2s执行）
                        log.info("线程{}进行写操作...", tid);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock.unlock();
                    }
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(20);

    }
}
