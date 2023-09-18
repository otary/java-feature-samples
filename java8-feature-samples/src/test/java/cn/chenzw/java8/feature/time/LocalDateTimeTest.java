package cn.chenzw.java8.feature.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@RunWith(JUnit4.class)
public class LocalDateTimeTest {

    @Test
    public void testNow() {
        LocalDateTime now = LocalDateTime.now();

        log.info("LocalDateTime now => {}", now); // => 2019-08-07T19:39:08.800
    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2019, 1, 21, 13, 45, 20);

        Assert.assertEquals("2019-01-21T13:45:20", localDateTime.toString());
    }

    /**
     * 时间戳 => LocalDateTime
     */
    @Test
    public void testTimeStamp2LocalDateTime() {
        Instant instant = Instant.ofEpochSecond(1692948472);
        LocalDateTime localDateTime = instant.atZone(
                ZoneId.of("Asia/Shanghai")
        ).toLocalDateTime();
        Assert.assertEquals("2023-08-25T15:27:52", localDateTime.toString());
    }

    /**
     * LocalDateTime => 时间戳
     */
    @Test
    public void testLocalDateTime2TimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 8, 25, 0, 0);

        Instant instant = localDateTime.atZone(
                ZoneId.of("Asia/Shanghai")
        ).toInstant();

        // 获取时间戳
        long timestamp = instant.getEpochSecond();
        Assert.assertEquals(1692892800, timestamp);
    }

}
