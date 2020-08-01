package cn.chenzw.java.feature.util.concurrent.locks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(JUnit4.class)
public class LockTests {


    @Test
    public void test() {

        Lock lock = new ReentrantLock();

        try {
            if (!lock.tryLock()) {
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
