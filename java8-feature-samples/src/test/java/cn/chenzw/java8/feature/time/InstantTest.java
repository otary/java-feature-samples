package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Instant;

@RunWith(JUnit4.class)
public class InstantTest {


    /**
     * 时间偏移
     */
    @Test
    public void testOfEpoch() {
        Instant instant = Instant.ofEpochSecond(60 * 24);
        Assert.assertEquals(1440, instant.getEpochSecond());
        Assert.assertEquals(1440000, instant.toEpochMilli());  // 毫秒

        // 增加偏移
        Instant instant2 = instant.plusSeconds(100);
        Assert.assertEquals(1540, instant2.getEpochSecond());

    }

    @Test
    public void testNow(){
        Instant now = Instant.now();

        Assert.assertEquals(System.currentTimeMillis(), now.toEpochMilli());
    }

}
