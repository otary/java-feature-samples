package cn.chenzw.java.feature.math;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

@RunWith(JUnit4.class)
public class BigDecimalTests {

    @Test
    public void testCompareTo() {
        BigDecimal a = new BigDecimal("1.23");
        BigDecimal b = new BigDecimal("1.230");
        Assert.assertTrue(a.compareTo(b) == 0);  // 使用compareTo比较值是否相等（推荐）
        Assert.assertFalse(a.equals(b));  // equals还比较了精度，所以不相等

        BigDecimal c = new BigDecimal("1.23");
        Assert.assertTrue(a.equals(c)); // 精度和值都相等，则相等

        BigDecimal d = new BigDecimal("1.231");
        Assert.assertTrue(a.compareTo(d) < 0); // 值大小比较
    }

    @Test
    public void testScale() {
        BigDecimal a = new BigDecimal(203.23553456);
        a = a.setScale(2, BigDecimal.ROUND_HALF_UP);  // 根据保留数字后一位>=5进行四舍五入
        Assert.assertEquals("203.24", a.toString());
        Assert.assertFalse(a.compareTo(new BigDecimal(203.24)) == 0);  // 精度后不相等

        BigDecimal a2 = new BigDecimal(203.25553456);
        a2 = a2.setScale(2, BigDecimal.ROUND_HALF_DOWN);  // 根据保留数字后一位>5进行四舍五入
        Assert.assertEquals("203.26", a2.toString());

        BigDecimal a3 = new BigDecimal(203.25553456);
        a3 = a3.setScale(2, BigDecimal.ROUND_DOWN);  // 保留指定的位数，后面所有直接去除
        Assert.assertEquals("203.25", a3.toString());

        BigDecimal a4 = new BigDecimal(203.25553456);
        a4 = a4.setScale(2, BigDecimal.ROUND_UP);  // 不管保留数字后面是大是小(0除外)都会进1
        Assert.assertEquals("203.26", a4.toString());



    }
}
