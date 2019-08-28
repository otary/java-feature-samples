package cn.chenzw.java9.feature.util.concurrent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@RunWith(JUnit4.class)
public class SubmissionPublisherTest {

    @Test
    public void test() {
        // 创建Publisher
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        // 注册Subscriber
        MySubscriber<String> subscriber = new MySubscriber<>();
        publisher.subscribe(subscriber);

        // 发布项目
        publisher.submit("测试内容");
        publisher.submit("测试内容2");
        publisher.close();
    }
}

/**
 * Subscriber实现类
 *
 * @param <T>
 */
class MySubscriber<T> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);

        System.out.println("-----------" + subscription + "-------------");
    }

    @Override
    public void onNext(T item) {
        System.out.println("-----------next:" + item + "-----------");

        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("-----------complete------------");
    }

}


