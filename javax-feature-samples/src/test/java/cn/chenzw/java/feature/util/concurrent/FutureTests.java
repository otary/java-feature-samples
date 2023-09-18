package cn.chenzw.java.feature.util.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.*;

@Slf4j
@RunWith(JUnit4.class)
public class FutureTests {

    @Test
    public void testRunnableFuture() throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> task1 = new FutureTask<>(new Callable<String>() {

            @Override
            public String call() throws Exception {
                log.info("开始任务1");
                Thread.sleep(5000);
                log.info("完成任务1");
                return "完成";
            }
        });

        Thread thread1 = new Thread(task1);
        thread1.start();
        String taskRst1 = task1.get();
        log.info("task1 => {}, isDone => {}, isCancelled => {}", taskRst1, task1.isDone(), task1.isCancelled());

        // 等待超时，直接返回
        Thread thread2 = new Thread(task1);
        thread2.start();
        String taskRst2 = task1.get(2, TimeUnit.SECONDS);
        log.info("task2 => {}, isDone => {}, isCancelled => {}", taskRst2, task1.isDone(), task1.isCancelled());

        // 取消任务
        boolean cancel = task1.cancel(true);

    }

    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        // 任务1：洗水壶->烧开水
        CompletableFuture<Void> task1 = CompletableFuture
                .runAsync(() -> {
                    log.info("T1:洗水壶...");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("T1:烧开水...");

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        // 任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> task2 = CompletableFuture
                .supplyAsync(() -> {
                    log.info("T2:洗茶壶...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("T2:洗茶杯...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    log.info("T2:拿茶叶...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "龙井";
                });

        // 任务3：任务1和任务2完成后执行：泡茶
        // thenCombine => 在两个任务都执行完成后，把两个任务结果合并
        // 两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果。
        // 两个任务是并行执行的，它们之间并没有先后依赖顺序。
        CompletableFuture<String> task3 = task1.thenCombine(task2, (t1, t2) -> {
            log.info("task1执行完,返回结果 => {}", t1);
            log.info("task2执行完,返回结果 => {}", t2);

            log.info("开始泡茶");
            return "结束!";
        });

        String rst = task3.get();
        // String rst = task3.join();

    }

    @Test
    public void testCompletableFuture2() {
        // thenCompose => 可以用于组合多个CompletableFuture，将前一个任务的返回结果作为下一个任务的参数，它们之间存在着业务逻辑上的先后顺序。
        // CompletableFuture.completedFuture()
    }
}
