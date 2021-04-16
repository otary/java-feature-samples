package cn.chenzw.java.feature.util.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@RunWith(JUnit4.class)
public class BlockingQueueTests {

    @Test
    public void testArrayBlockingQueue() {
        ArrayBlockingQueue abq = new ArrayBlockingQueue<String>(10);

        // 不抛出异常（offer、poll、peek）
        for (int i = 0; i < 15; i++) {
            String value = "aaa" + i;
            // 添加元素（队列满了返回false）
            boolean success = abq.offer(value);

            log.info("offer [{}] => {}", value, success);
        }

        for (int i = 0; i < 15; i++) {
            // 从头部取值
            Object value = abq.poll();

            log.info("poll => {}", value);
        }

        for (int i = 0; i < 15; i++) {
            // 从尾部取值
            Object value = abq.peek();

            log.info("peek => {}", value);
        }


        // 空队列或满队列会抛出异常
        for (int i = 0; i < 15; i++) {
            String value = "aaa" + i;
            // 添加元素（队列满了抛出异常）
            try {
                abq.add(value);

                log.info("add [{}] success!", value);
            } catch (IllegalStateException ex) {
                log.error("add [{}] fail!", value);
            }

        }

        for (int i = 0; i < 15; i++) {
            try {
                // 从头部移除（队列空了抛出异常）
                Object value = abq.remove();
                log.info("remove => {}", value);
            } catch (Exception e) {
                log.error("remove fail!");
            }
        }

        for (int i = 0; i < 15; i++) {
            try {
                // 从尾部移除（队列空了抛出异常）
                Object value = abq.element();
                log.info("element => {}", value);
            } catch (Exception ex) {
                log.error("element fail!");
            }
        }
    }
}
