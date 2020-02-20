package cn.chenzw.java.feature.util.concurrent.locks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(JUnit4.class)
public class LockTests {


    @Test
    public void test() throws InterruptedException {

        Lock lock = new ReentrantLock();


        lock.lock();

        System.out.println(lock.tryLock());

        lock.unlock();

        lock.lock();
        Thread.sleep(2000);
        lock.unlock();
        System.out.println(lock.tryLock());

    }
}
