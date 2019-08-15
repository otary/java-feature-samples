package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalTime;

@RunWith(JUnit4.class)
public class LocalTimeTest {

    @Test
    public void testNow() {
        LocalTime localTime = LocalTime.now();

        System.out.println(localTime.toString());  // => 19:26:56.381
    }

    @Test
    public void testLocalTime(){
        LocalTime time = LocalTime.of(13, 45, 20);

        Assert.assertEquals(13, time.getHour());
        Assert.assertEquals(45, time.getMinute());
        Assert.assertEquals(20, time.getSecond());
    }
}