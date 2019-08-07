package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;

@RunWith(JUnit4.class)
public class LocalDateTimeTest {

    @Test
    public void testNow() {
        LocalDateTime now = LocalDateTime.now();

        System.out.println(now); // => 2019-08-07T19:39:08.800
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, 1, 21, 13, 45, 20);

        Assert.assertEquals("2019-01-21T13:45:20", localDateTime.toString());
    }
}
