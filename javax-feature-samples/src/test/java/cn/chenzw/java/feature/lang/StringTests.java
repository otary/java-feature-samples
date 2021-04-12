package cn.chenzw.java.feature.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;

@RunWith(JUnit4.class)
public class StringTests {


    @Test
    public void testCharset() throws UnsupportedEncodingException {

        // 查看本地的默认编码
        Assert.assertEquals("UTF-8", System.getProperty("file.encoding"));

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


        String s = Integer.toBinaryString(200);

        System.out.println("----------" + s);
    }

}
