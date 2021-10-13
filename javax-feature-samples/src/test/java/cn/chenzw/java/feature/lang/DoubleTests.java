package cn.chenzw.java.feature.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@Slf4j
@RunWith(JUnit4.class)
public class DoubleTests {

    @Test
    public void testCompare() {
        Double a1 = new Double("10.234");
        Double b1 = new Double("11");
        Assert.assertTrue(a1 < b1);
        Assert.assertTrue(a1.compareTo(b1) < 0);

        Double a2 = new Double("10.234");
        Double b2 = new Double("10.2340");
        Assert.assertTrue(a2.compareTo(b2) == 0);
        Assert.assertFalse(a2 == b2); // 不能用等于号

        Double a3 = new Double("3");
        Double b3 = new Double("4");
        Assert.assertTrue((a3 * b3) == 12);
        Assert.assertTrue(new Double("12").compareTo(a3 * b3) == 0);

        Double t = new Double("10");
        Double s = new Double("12");
        Double x = new Double("3");

        double result = (s - t) / t * 100 / x;

        // 取2位小数
        BigDecimal bigDecimal = new BigDecimal(result);
        BigDecimal roundDown = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);

        log.info("BigDecimal.ROUND_DOWN => {}", roundDown.doubleValue());
    }
}
