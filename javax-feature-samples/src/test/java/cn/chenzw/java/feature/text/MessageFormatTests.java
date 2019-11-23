package cn.chenzw.java.feature.text;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.MessageFormat;

@RunWith(JUnit4.class)
public class MessageFormatTests {

    @Test
    public void test() {
        String msg = MessageFormat.format("Hello,{0}", "张三");

        Assert.assertEquals("Hello,张三", msg);
    }
}
