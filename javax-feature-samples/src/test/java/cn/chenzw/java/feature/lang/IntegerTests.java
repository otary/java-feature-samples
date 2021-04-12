package cn.chenzw.java.feature.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IntegerTests {

    /**
     * 数值 转 二进制
     */
    @Test
    public void testToBinaryString() {
        String binaryStr = Integer.toBinaryString(Integer.MAX_VALUE);
        Assert.assertEquals("1111111111111111111111111111111", binaryStr);

        // 200 => 11001000
        String binaryStr2 = Integer.toBinaryString(200);
        Assert.assertEquals("11001000", binaryStr2);

    }

    /**
     * 数值 转 十六进制
     */
    @Test
    public void testToHexString() {
        String hexStr = Integer.toHexString(Integer.MAX_VALUE);
        Assert.assertEquals("7fffffff", hexStr);

        // 200 => c8
        String hexStr2 = Integer.toHexString(200);
        Assert.assertEquals("c8", hexStr2);
    }
}
