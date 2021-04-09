package cn.chenzw.java7.feature.util.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RunWith(JUnit4.class)
public class ThreadLocalRandomTests {


    /**
     * 线程安全的随机数生成器
     */
    @Test
    public void test() {
        int iRandom = ThreadLocalRandom.current().nextInt();
        log.info("iRandom => {}", iRandom);

        // 范围随机数
        int iRangeRandom = ThreadLocalRandom.current().nextInt(2, 4);
        log.info("iRangeRandom => {}", iRangeRandom);
    }
}
