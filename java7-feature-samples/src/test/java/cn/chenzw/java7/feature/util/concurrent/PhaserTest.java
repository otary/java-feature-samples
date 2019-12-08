package cn.chenzw.java7.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class PhaserTest {

    @Test
    public void test() throws InterruptedException {
        MyPhaser myPhaser = new MyPhaser();

        ExecutorService es = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            es.submit(new MyTask(myPhaser));
            myPhaser.register();  // 用于注册线程数
        }

        es.awaitTermination(30, TimeUnit.SECONDS);
    }
}


/**
 * 阶段控制器
 */
class MyPhaser extends Phaser {

    /**
     * 每一阶段执行完毕则调用一次
     *
     * @param phase             阶段值(初始值:0), 每进入下一个阶段+1
     * @param registeredParties 线程数
     * @return true: 停止Phaser; false: 进入下一阶段
     */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                System.out.println("阶段:[" + phase + "], 线程数:[" + registeredParties + "]");
                return false;
            case 1:
                System.out.println("阶段:[" + phase + "], 线程数:[" + registeredParties + "]");
                return false;
            case 2:
                System.out.println("阶段:[" + phase + "], 线程数:[" + registeredParties + "]");
                return false;
            default:
                System.out.println("结束工作");
                return true;
        }
    }
}


class MyTask implements Runnable {

    private Phaser phaser;

    public MyTask(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        Random random = new Random();

        System.out.println(Thread.currentThread().getId() + "准备");

        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getId() + "开始第一道工序");

        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getId() + "结束第一道工序");

        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getId() + "开始第二道工序");

        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getId() + "结束第二道工序");

        phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread().getId() + "开始第三道工序");

        try {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getId() + "结束第三道工序");

        phaser.arriveAndAwaitAdvance();
    }
}