package cn.chenzw.java.feature.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@RunWith(JUnit4.class)
public class DequeTests {

    @Test
    public void testLinkedBlockingDeque() {
        // 双端队列
        LinkedBlockingDeque<Object> lbd = new LinkedBlockingDeque<>();

        // 作为栈使用（先进后出）
        for (int i = 0; i < 15; i++) {
            String value = "aaa" + i;
            lbd.push(value);

            log.info("push => {}", value);
        }

        for (int i = 0; i < 15; i++) {
            Object value = lbd.pop();

            log.info("pop => {}", value);
        }


        // @TODO 作为队列使用（先进先出）

    }

}
