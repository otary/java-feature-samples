package cn.chenzw.java7.feature.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class StringTests {

    /**
     * 字符串 => Char数组
     */
    @Test
    public void testToCharArray() {
        String str = "abc";
        char[] chars = str.toCharArray();

        Assert.assertArrayEquals(new char[]{
                'a', 'b', 'c'
        }, chars);
    }

    /**
     * 提取指定的char字符
     */
    @Test
    public void testCharAt() {
        String str = "abc";

        Assert.assertEquals('a', str.charAt(0));
        Assert.assertEquals('b', str.charAt(1));
        Assert.assertEquals('c', str.charAt(2));
    }
}
