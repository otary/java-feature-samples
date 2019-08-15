package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Duration;
import java.time.LocalDateTime;

@RunWith(JUnit4.class)
public class DurationTest {

    @Test
    public void testDurationBetween() {

        LocalDateTime localDateTime = LocalDateTime.of(2019, 1, 1, 10, 2, 3);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, 3, 12, 11, 23, 3);

        Duration between = Duration.between(localDateTime, localDateTime2);
        // 相距天数
        Assert.assertEquals(70, between.toDays());

        // 相距小时数
        Assert.assertEquals(1681, between.toHours());

        // 相距分钟数
        Assert.assertEquals(100881, between.toMinutes());

        // 相距秒数
        Assert.assertEquals(6052860, between.getSeconds());

        // 相距毫秒数
        Assert.assertEquals(6052860000L, between.toMillis());
    }

    @Test
    public void testPlus() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, 1, 1, 10, 2, 3);
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, 3, 12, 11, 23, 3);

        Duration duration = Duration.between(localDateTime, localDateTime2);

        // 添加秒数
        Duration duration2 = duration.plusSeconds(100);
        Assert.assertEquals(6052960, duration2.getSeconds());

        // 添加日期
        Duration duration3 = duration.plusDays(1);
        Assert.assertEquals(6139260, duration3.getSeconds());
    }
}
