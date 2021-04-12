package cn.chenzw.java.feature.lang;

import cn.chenzw.javax.feature.lang.ExceptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class ExceptionTests {

    @Test
    public void test() throws InterruptedException {
        ExceptionService exceptionService = new ExceptionService();
        try {
            exceptionService.go();
        } catch (Exception ex) {

        }
        TimeUnit.DAYS.sleep(1);
    }
}
