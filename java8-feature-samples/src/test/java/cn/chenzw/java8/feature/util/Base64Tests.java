package cn.chenzw.java8.feature.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Base64;

@RunWith(JUnit4.class)
public class Base64Tests {

    /**
     * 加密
     */
    @Test
    public void testEncode() {
        byte[] encode = Base64.getEncoder().encode("xxxx".getBytes());
        Assert.assertEquals("eHh4eA==", new String(encode));

        // 生成String
        String encodeStr = Base64.getEncoder().encodeToString("xxxx".getBytes());
        Assert.assertEquals("eHh4eA==", encodeStr);
    }

    @Test
    public void testDecode() {
        byte[] decode = Base64.getDecoder().decode("eHh4eA==");
        Assert.assertEquals("xxxx", new String(decode));
    }

    /**
     * 支持MIME（包含换行）
     */
    @Test
    public void testMimeEncode() {
        String encodeStr = Base64.getMimeEncoder().encodeToString("xxxx".getBytes());
        Assert.assertEquals("eHh4eA==", encodeStr);
    }

    @Test
    public void testMimeDecode() {
        byte[] decode = Base64.getMimeDecoder().decode("eHh4eA==");
        Assert.assertEquals("xxxx", new String(decode));
    }

    @Test
    public void testUrlEncode() {
        String encodeStr = Base64.getUrlEncoder().encodeToString("xxxx".getBytes());
        Assert.assertEquals("eHh4eA==", encodeStr);
    }

    @Test
    public void testUrlDecode() {
        byte[] decode = Base64.getUrlDecoder().decode("eHh4eA==");
        Assert.assertEquals("xxxx", new String(decode));
    }
}
