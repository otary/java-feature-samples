package cn.chenzw.java.feature.util.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

@Slf4j
@RunWith(JUnit4.class)
public class BlockingQueueTests {

    @Test
    public void testArrayBlockingQueue() {
        // 有界队列（FIFO）
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


        // 抛出异常（空队列或满队列 - add、remove、element）
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


        // 阻塞
        for (int i = 0; i < 15; i++) {
            String value = "aaa" + i;
            // 添加元素（队列满时阻塞）
            try {
                abq.put(value);

                log.info("put [{}] success!", value);
            } catch (IllegalStateException | InterruptedException ex) {
                log.error("put [{}] fail!", value);
            }

        }

        for (int i = 0; i < 15; i++) {
            try {
                // 从尾部移除（队列空时阻塞）
                Object value = abq.take();
                log.info("put => {}", value);
            } catch (Exception ex) {
                log.error("put fail!");
            }
        }

    }


    /**
     * 优先级队列
     */
    @Test
    public void testPriorityBlockingQueue() {
        // 排序无界队列
        PriorityBlockingQueue<String> pbq = new PriorityBlockingQueue<>(10, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.valueOf(s1) - Integer.valueOf(s2);
            }
        });

        //
        for (int i = 0; i < 15; i++) {
            String value = String.valueOf(i);
            // 添加元素（无界队列不会满）
            pbq.put(value);

            log.info("put [{}]", value);
        }

        for (int i = 0; i < 15; i++) {
            try {
                // 从尾部移除
                Object value = pbq.take();
                log.info("take => {}", value);
            } catch (Exception ex) {
                log.error("take fail!");
            }
        }


    }
}
