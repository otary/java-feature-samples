package cn.chenzw.java11.feature;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.Stream;

@RunWith(JUnit4.class)
public class StringTest {

    @Test
    public void testRepeat() {
        // 重复2次
        var name = "chenzw";
        var result = name.repeat(2);
        Assert.assertEquals("chenzwchenzw", result);

        // 重复0次返回空字符串
        var name2 = "chenzw";
        var result2 = name.repeat(0);
        Assert.assertEquals("", result2);
    }

    @Test
    public void testIsBlank() {

        // 空字符串
        var blankName = "";
        Assert.assertTrue(blankName.isBlank());


        // 空格
        var spaceName = " ";
        Assert.assertTrue(spaceName.isBlank());
    }

    /**
     * 多行字符
     */
    @Test
    public void testLines() {
        var lineString = "abc\negf\nway\nlau";
        Stream<String> lineStream = lineString.lines();
        Assert.assertEquals(4, lineStream.count());
    }
}
