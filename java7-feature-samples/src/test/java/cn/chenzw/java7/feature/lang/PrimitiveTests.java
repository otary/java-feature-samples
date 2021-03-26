package cn.chenzw.java7.feature.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 基本数据类型测试
 */
@RunWith(JUnit4.class)
public class PrimitiveTests {


    /**
     * 字符串 与 数字 比大小
     */
    @Test
    public void testEquals() {
        Integer ia = 1;
        String sa = "1";

        // 相 == 比较，接着比较类型，类型不相同，直接false
        Assert.assertFalse(sa.equals(ia));
    }
}
