package cn.chenzw.java.feature.lang;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IntegerTests {

    /**
     * 十进制 转 二进制
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
     * 十进制 转 十六进制
     */
    @Test
    public void testToHexString() {
        String hexStr = Integer.toHexString(Integer.MAX_VALUE);
        Assert.assertEquals("7fffffff", hexStr);

        // 200 => c8
        String hexStr2 = Integer.toHexString(200);
        Assert.assertEquals("c8", hexStr2);
    }

    /**
     * 十进制 转 八进制
     */
    @Test
    public void testToOctalString() {
        String octalStr = Integer.toOctalString(Integer.MAX_VALUE);
        Assert.assertEquals("17777777777", octalStr);

        String octalStr2 = Integer.toOctalString(200);
        Assert.assertEquals("310", octalStr2);
    }


    /**
     * 缓存
     */
    @Test
    public void testCache() {
        // -128 ~ 127 走缓存（如果是new的，则都不想等）
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i11 = Integer.valueOf(100);
        Assert.assertTrue(i1 == i2);
        Assert.assertTrue(i1 == i11);

        // 超过127的不走缓存
        Integer i3 = 500;
        Integer i4 = 500;
        Assert.assertFalse(i3 == i4);
    }

    /**
     * 数组拆箱、装箱（默认数组无法自动装箱、拆箱）
     */
    @Test
    public void testArray() {
        Integer[] i1 = {1, 2, 3, 4};
        int[] i3 = ArrayUtils.toPrimitive(i1);  // 数组拆箱（Integer[] => int[]）
        Integer[] i4 = ArrayUtils.toObject(new int[10]);  // 数组装箱（int[] => Integer[]）
    }

}
