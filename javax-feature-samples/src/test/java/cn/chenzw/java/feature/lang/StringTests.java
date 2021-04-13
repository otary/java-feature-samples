package cn.chenzw.java.feature.lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@RunWith(JUnit4.class)
public class StringTests {


    @Test
    public void testCharset() throws UnsupportedEncodingException {

        // 查看本地的默认编码
        Assert.assertEquals("UTF-8", Charset.defaultCharset().name());

        // UTF-8每个中文占用3字节,每个英文占用1字节
        byte[] abytes = "中".getBytes();
        Assert.assertEquals(3, abytes.length);
        abytes = "a".getBytes();
        Assert.assertEquals(1, abytes.length);

        byte[] bbytes = "中".getBytes("UTF-8");
        Assert.assertEquals(3, bbytes.length);

        // UTF-16每个中文占用4字节,每个英文占用4字节
        byte[] cbytes = "中".getBytes("UTF-16");
        Assert.assertEquals(4, cbytes.length);

        cbytes = "a".getBytes("UTF-16");
        Assert.assertEquals(4, cbytes.length);


        // GBK每个中文占用2字节，每个英文占用1字节
        byte[] dbytes = "中".getBytes("GBK");
        Assert.assertEquals(2, dbytes.length);

        dbytes = "a".getBytes("GBK");
        Assert.assertEquals(1, dbytes.length);

        // 总结：无论使用何种编码，中文字符占用>1字节（可通过判断字符长度和占用字节数判断是否存在中文）

    }

    @Test
    public void testIntern() {
        String a = "aaa";
        String b = new String("aaa"); // 堆中新分配
        String c = "aaa";
        String d = "a" + "a" + "a"; // JVM编译时自动优化
        String e = a + "";  // 使用StringBuilder进行字符串拼接，所以在堆中创建新对象

        Assert.assertTrue(a == c);  // 共用常量池
        Assert.assertTrue(a == d);  // JVM编译时自动优化

        Assert.assertFalse(a == b);  // 两者堆地址不一样
        Assert.assertTrue(a.intern() == b.intern());  // 从常量池中取的是一样的

        Assert.assertFalse(a == e); //
    }

    @Test
    public void testReplaceAll() {
        // 去除数字
        String result = "abcdef1234xxx".replaceAll("\\d+", "");
        Assert.assertEquals("abcdefxxx", result);

    }



}
